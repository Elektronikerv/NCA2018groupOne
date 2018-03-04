package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
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
public class FulfillmentOrderDaoImpl implements FulfillmentOrderDao {

    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert fulfillmentOrderInsert;
    private FulfillmentOrderWithDetailExtractor fulfillmentOrderWithDetailExtractor;
    private QueryService queryService;
    private UserDao userDao;
    private OrderDao orderDao;

    @Autowired
    public FulfillmentOrderDaoImpl(QueryService queryService, UserDao userDao, OrderDao orderDao) {
        this.queryService = queryService;
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.fulfillmentOrderInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("fulfillment_orders")
                .usingGeneratedKeyColumns("id");
        fulfillmentOrderWithDetailExtractor = new FulfillmentOrderDaoImpl.FulfillmentOrderWithDetailExtractor();
    }

    @Override
    public FulfillmentOrder create(FulfillmentOrder fulfillmentOrder) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", fulfillmentOrder.getOrder().getId())
                .addValue("ccagent_id", fulfillmentOrder.getCcagent().getId())
                .addValue("courier_id",
                        Objects.isNull(fulfillmentOrder.getCourier()) ? null : fulfillmentOrder.getCourier().getId())
                .addValue("confirmation_time",
                        Objects.isNull(fulfillmentOrder.getConfirmationTime()) ? null : Timestamp.valueOf(fulfillmentOrder.getConfirmationTime()))
                .addValue("shipping_time",
                        Objects.isNull(fulfillmentOrder.getShippingTime()) ? null : Timestamp.valueOf(fulfillmentOrder.getShippingTime()))
                .addValue("attempt", fulfillmentOrder.getAttempt());
        Long id = fulfillmentOrderInsert.executeAndReturnKey(parameterSource).longValue();
        fulfillmentOrder.setId(id);
        return findById(fulfillmentOrder.getId());
    }

    @Override
    public FulfillmentOrder findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("fulfillment_order.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<FulfillmentOrder> fulfillmentOrders = jdbcTemplate.query(findUserByIdQuery, parameterSource, fulfillmentOrderWithDetailExtractor);
        return fulfillmentOrders.isEmpty() ? null : fulfillmentOrders.get(0);
    }

    @Override
    public List<FulfillmentOrder> findByStatusByCourier(Long orderStatusId, Long courierId) {
        String findByStatusByCourierQuery = queryService.getQuery("fulfillment_order.findByStatusByCourier");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_status_id", orderStatusId)
                .addValue("courier_id", courierId);
        List<FulfillmentOrder> fulfillmentOrders = jdbcTemplate.query(findByStatusByCourierQuery, parameterSource, fulfillmentOrderWithDetailExtractor);
        return fulfillmentOrders.isEmpty() ? null : fulfillmentOrders;

    }

    @Override
    public FulfillmentOrder update(FulfillmentOrder fulfillmentOrder) {
        String update = queryService.getQuery("fulfillment_order.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", fulfillmentOrder.getId())
                .addValue("order_id", fulfillmentOrder.getOrder().getId())
                .addValue("ccagent_id", fulfillmentOrder.getCcagent().getId())
                .addValue("courier_id",
                        Objects.isNull(fulfillmentOrder.getCourier()) ? null : fulfillmentOrder.getCourier().getId())
                .addValue("confirmation_time",
                        Objects.isNull(fulfillmentOrder.getConfirmationTime()) ? null : Timestamp.valueOf(fulfillmentOrder.getConfirmationTime()))
                .addValue("shipping_time",
                        Objects.isNull(fulfillmentOrder.getShippingTime()) ? null : Timestamp.valueOf(fulfillmentOrder.getShippingTime()))
                .addValue("attempt", fulfillmentOrder.getAttempt());
        jdbcTemplate.update(update, parameterSource);
        return fulfillmentOrder;


    }

    @Override
    public FulfillmentOrder updateWithInternals(FulfillmentOrder fulfillmentOrder) {
        Order order = orderDao.update(fulfillmentOrder.getOrder());
        fulfillmentOrder.setOrder(order);
        return update(fulfillmentOrder);

    }

    @Override
    public boolean delete(FulfillmentOrder fulfillmentOrder) {
        return delete(fulfillmentOrder.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("fulfillment_order.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class FulfillmentOrderWithDetailExtractor implements ResultSetExtractor<List<FulfillmentOrder>>, TimestampExtractor {

        @Override
        public List<FulfillmentOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<FulfillmentOrder> fulfillmentOrders = new ArrayList<>();
            while (rs.next()) {
                FulfillmentOrder fulfillmentOrder = new RealFulfillmentOrder();
                fulfillmentOrder.setId(rs.getLong("id"));

                Long orderId = rs.getLong("order_id");
                if (orderId != 0) {
                    Order order = new ProxyOrder(orderDao);
                    order.setId(orderId);
                    fulfillmentOrder.setOrder(order);
                }

                Long ccagentId = rs.getLong("ccagent_id");
                if (ccagentId != 0) {
                    User ccagent = new ProxyUser(userDao);
                    ccagent.setId(ccagentId);
                    fulfillmentOrder.setCcagent(ccagent);
                }

                Long courierId = rs.getLong("courier_id");
                if (courierId != 0) {
                    User courier = new ProxyUser(userDao);
                    courier.setId(courierId);
                    fulfillmentOrder.setCourier(courier);
                }

                fulfillmentOrder.setConfirmationTime(getLocalDateTime(rs.getTimestamp("confirmation_time")));
                fulfillmentOrder.setShippingTime(getLocalDateTime(rs.getTimestamp("shipping_time")));
                fulfillmentOrder.setAttempt(rs.getInt("attempt"));
                fulfillmentOrders.add(fulfillmentOrder);
            }
            return fulfillmentOrders;
        }
    }

}

