package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.*;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyAddress;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyOffice;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyOrder;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyUser;
import ncadvanced2018.groupeone.parent.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@NoArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert orderInsert;
    private OrderWithDetailExtractor orderWithDetailExtractor;
    private QueryService queryService;
    private UserDao userDao;
    private OrderStatusDao orderStatusDao;
    private AddressDao addressDao;
    private OfficeDao officeDao;

    @Autowired
    public OrderDaoImpl(QueryService queryService, UserDao userDao, OrderStatusDao orderStatusDao, AddressDao addressDao, OfficeDao officeDao) {
        this.queryService = queryService;
        this.userDao = userDao;
        this.orderStatusDao = orderStatusDao;
        this.addressDao = addressDao;
        this.officeDao = officeDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.orderInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
        orderWithDetailExtractor = new OrderWithDetailExtractor();
    }

    @Override
    public Order create(Order order) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("office_id",
                        Objects.isNull(order.getOffice()) ? null : order.getOffice().getId())
                .addValue("sender_address_id", order.getSenderAddress().getId())
                .addValue("receiver_address_id", order.getReceiverAddress().getId())
                .addValue("creation_time",
                        Timestamp.valueOf(order.getCreationTime()))
                .addValue("execution_time",
                        Objects.isNull(order.getExecutionTime()) ? null : Timestamp.valueOf(order.getExecutionTime()))
                .addValue("parent_id",
                        Objects.isNull(order.getParent()) ? null : order.getParent().getId())
                .addValue("user_id", order.getUser().getId())
                .addValue("description", order.getDescription())
                .addValue("feedback", order.getFeedback())
                .addValue("order_status_id", order.getOrderStatus().getId());
        Long id = orderInsert.executeAndReturnKey(parameterSource).longValue();
        order.setId(id);
        return order;
    }

    @Override
    public Order findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("order.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List <Order> orders = jdbcTemplate.query(findUserByIdQuery, parameterSource, orderWithDetailExtractor);
        return orders.isEmpty() ? null : orders.get(0);
    }

    @Override
    public boolean update(Order order) {
        String update = queryService.getQuery("order.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("office_id",
                        Objects.isNull(order.getOffice()) ? null : order.getOffice().getId())
                .addValue("sender_address_id", order.getSenderAddress().getId())
                .addValue("receiver_address_id", order.getReceiverAddress().getId())
                .addValue("creation_time",
                        Timestamp.valueOf(order.getCreationTime()))
                .addValue("execution_time",
                        Objects.isNull(order.getExecutionTime()) ? null : Timestamp.valueOf(order.getExecutionTime()))
                .addValue("parent_id",
                        Objects.isNull(order.getParent()) ? null : order.getParent().getId())
                .addValue("user_id", order.getUser().getId())
                .addValue("description", order.getDescription())
                .addValue("feedback", order.getFeedback())
                .addValue("order_status_id", order.getOrderStatus().getId());
        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Order entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("order.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class OrderWithDetailExtractor implements ResultSetExtractor <List <Order>>, TimestampExtractor {

        @Override
        public List <Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <Order> orders = new ArrayList <>();
            while (rs.next()) {
                Order order = new RealOrder();
                order.setId(rs.getLong("id"));

                Long officeId = rs.getLong("office_id");
                if (officeId != 0) {
                    Office office = new ProxyOffice(officeDao);
                    office.setId(officeId);
                    order.setOffice(office);
                }

                Long senderAddressId = rs.getLong("sender_address_id");
                if (senderAddressId != 0) {
                    Address senderAddress = new ProxyAddress(addressDao);
                    senderAddress.setId(senderAddressId);
                    order.setSenderAddress(senderAddress);
                }

                Long receiverAddressId = rs.getLong("receiver_address_id");
                if (receiverAddressId != 0) {
                    Address receiverAddress = new ProxyAddress(addressDao);
                    receiverAddress.setId(receiverAddressId);
                    order.setSenderAddress(receiverAddress);
                }

                order.setCreationTime(getLocalDateTime(rs.getTimestamp("creation_time")));
                order.setExecutionTime(getLocalDateTime(rs.getTimestamp("execution_time")));

                Long parentId = rs.getLong("parent_id");
                if (parentId != 0) {
                    Order parentOrder = new ProxyOrder(OrderDaoImpl.this);
                    parentOrder.setId(parentId);
                    order.setParent(parentOrder);
                }

                order.setFeedback(rs.getString("feedback"));
                order.setDescription(rs.getString("description"));

                Long orderStatusId = rs.getLong("order_status_id");
                if (orderStatusId != 0) {
                    OrderStatus orderStatus = OrderStatus.valueOf(orderStatusId);
                    order.setOrderStatus(orderStatus);
                }

                Long userId = rs.getLong("user_id");
                if (userId != 0) {
                    User user = new ProxyUser(userDao);
                    user.setId(userId);
                    order.setUser(user);
                }

                orders.add(order);
            }
            return orders;
        }
    }
}