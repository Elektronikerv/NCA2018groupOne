package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.*;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@NoArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert orderInsert;
    private OrderWithDetailExtractor orderWithDetailExtractor;
    private OrderClientStatisticExtractor orderClientStatisticExtractor;
    private OrderOfficeStatisticExtractor orderOfficeStatisticExtractor;
    private OrderGeneralStatisticExtractor orderGeneralStatisticExtractor;
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
        orderClientStatisticExtractor = new OrderClientStatisticExtractor();
        orderOfficeStatisticExtractor = new OrderOfficeStatisticExtractor();
        orderGeneralStatisticExtractor = new OrderGeneralStatisticExtractor();
    }

    @Override
    public Order create(Order order) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("office_id",
                        Objects.isNull(order.getOffice()) ? null : order.getOffice().getId())
                .addValue("sender_address_id", Objects.isNull(order.getSenderAddress()) ? null : order.getSenderAddress().getId())
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
        List<Order> orders = jdbcTemplate.query(findUserByIdQuery, parameterSource, orderWithDetailExtractor);
        return orders.isEmpty() ? null : orders.get(0);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        String findByUserId = queryService.getQuery("order.findByUserId");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", userId);
        List<Order> orders = jdbcTemplate.query(findByUserId, parameterSource, orderWithDetailExtractor);
        return orders;
    }

    @Override
    public Order update(Order order) {
        String update = queryService.getQuery("order.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("office_id",
                        Objects.isNull(order.getOffice()) ? null : order.getOffice().getId())
                .addValue("sender_address_id", Objects.isNull(order.getSenderAddress()) ? null : order.getSenderAddress().getId())
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
        jdbcTemplate.update(update, parameterSource);
        return findById(order.getId());
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

    @Override
    public List<Order> findAllOrders(){
        String findAllOrders = queryService.getQuery("order.findAllOrders");
        List<Order> orders = jdbcTemplate.query(findAllOrders, orderWithDetailExtractor);
        return orders;
    }

    @Override
    public List<Order> findAllOpenOrders() {
        String findAllOrders = queryService.getQuery("order.findAllOpenOrders");
        List<Order> orders = jdbcTemplate.query(findAllOrders, orderWithDetailExtractor);
        return orders;
    }

    @Override
    public List <Order> findAllConfirmedOrders() {
        String findAllConfirmedOrders = queryService.getQuery("order.findAllConfirmedOrders");
        List <Order> orders = jdbcTemplate.query(findAllConfirmedOrders, orderWithDetailExtractor);
        return orders;
    }

    @Override
    public GeneralStatistic findClientStatisticByCompany(String startDate, String endDate) {
        String findClientStatisticByCompanyQuery = queryService.getQuery("order.avg_min_max_sum_by_client");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <GeneralStatistic> generalStatistics = jdbcTemplate.query(findClientStatisticByCompanyQuery, parameterSource, orderGeneralStatisticExtractor);
        return generalStatistics.isEmpty() ? null : generalStatistics.get(0);
    }

    @Override
    public List <UserStatistic> findPersonalClientStatistic(String startDate, String endDate) {
        String findPersonalClientStatisticByManagerQuery = queryService.getQuery("order.clientStat");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <UserStatistic> personalCategoryStatistics = jdbcTemplate.query(findPersonalClientStatisticByManagerQuery, parameterSource, orderClientStatisticExtractor);
        return personalCategoryStatistics.isEmpty() ? null : personalCategoryStatistics;
    }

    @Override
    public GeneralStatistic findOfficeStatisticByCompany(String startDate, String endDate) {
        String findOfficeStatisticByCompanyQuery = queryService.getQuery("order.avg_min_max_sum_by_office");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <GeneralStatistic> generalStatistics = jdbcTemplate.query(findOfficeStatisticByCompanyQuery, parameterSource, orderGeneralStatisticExtractor);
        return generalStatistics.isEmpty() ? null : generalStatistics.get(0);
    }

    @Override
    public List <OfficeStatistic> findPersonalOfficeStatistic(String startDate, String endDate) {
        String findPersonalOfficeStatisticByManagerQuery = queryService.getQuery("order.officeStat");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <OfficeStatistic> personalCategoryStatistics = jdbcTemplate.query(findPersonalOfficeStatisticByManagerQuery, parameterSource, orderOfficeStatisticExtractor);
        return personalCategoryStatistics.isEmpty() ? null : personalCategoryStatistics;
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
                    order.setReceiverAddress(receiverAddress);
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

    private final class OrderOfficeStatisticExtractor implements ResultSetExtractor <List <OfficeStatistic>> {

        @Override
        public List <OfficeStatistic> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <OfficeStatistic> categoryStatistics = new ArrayList <>();
            while (rs.next()) {
                OfficeStatistic categoryStatistic = new OfficeStatistic();
                categoryStatistic.setId(rs.getLong("id"));
                categoryStatistic.setCount(rs.getLong("orders"));
                categoryStatistic.setName(rs.getString("name"));
                categoryStatistic.setPercentageByCompany(rs.getDouble("per_company"));
                categoryStatistic.setDifferenceBetweenAvgCompany(rs.getDouble("diff_company"));

                categoryStatistics.add(categoryStatistic);
            }
            return categoryStatistics;
        }
    }

    private final class OrderClientStatisticExtractor implements ResultSetExtractor <List <UserStatistic>> {

        @Override
        public List <UserStatistic> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <UserStatistic> categoryStatistics = new ArrayList <>();
            while (rs.next()) {
                UserStatistic categoryStatistic = new UserStatistic();
                categoryStatistic.setId(rs.getLong("id"));
                categoryStatistic.setLastName(rs.getString("last_name"));
                categoryStatistic.setFirstName(rs.getString("first_name"));
                categoryStatistic.setStatus(rs.getString("status"));
                categoryStatistic.setCount(rs.getLong("orders"));
                categoryStatistic.setPercentageByCompany(rs.getDouble("per_company"));
                categoryStatistic.setDifferenceBetweenAvgCompany(rs.getDouble("diff_company"));

                categoryStatistics.add(categoryStatistic);
            }
            return categoryStatistics;
        }
    }

    private final class OrderGeneralStatisticExtractor implements ResultSetExtractor <List <GeneralStatistic>> {

        @Override
        public List <GeneralStatistic> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <GeneralStatistic> generalStatistics = new ArrayList <>();
            while (rs.next()) {
                GeneralStatistic generalStatistic = new GeneralStatistic();
                generalStatistic.setMax(rs.getLong("max"));
                generalStatistic.setMin(rs.getLong("min"));
                generalStatistic.setCount(rs.getLong("count"));
                generalStatistic.setAvg(rs.getDouble("avg"));

                generalStatistics.add(generalStatistic);
            }
            return generalStatistics;
        }
    }
}