package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.*;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
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

import static ncadvanced2018.groupeone.parent.dto.OrderAction.GIVE;
import static ncadvanced2018.groupeone.parent.dto.OrderAction.TAKE;

@Repository
@NoArgsConstructor
public class FulfillmentOrderDaoImpl implements FulfillmentOrderDao {

    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert fulfillmentOrderInsert;
    private FulfillmentOrderWithDetailExtractor fulfillmentOrderWithDetailExtractor;
    private FulfillmentOrderGeneralStatisticExtractor fulfillmentOrderGeneralStatisticExtractor;
    private FulfillmentOrderEmpStatisticExtractor fulfillmentOrderStatisticEmpExtractor;
    private QueryService queryService;
    private UserDao userDao;
    private OrderDao orderDao;
    private OfficeDao officeDao;
    private AddressDao addressDao;
    private CourierWayExtractor courierWayExtractor;

    @Autowired
    public FulfillmentOrderDaoImpl(QueryService queryService, UserDao userDao, OrderDao orderDao, OfficeDao officeDao,
                                   AddressDao addressDao) {
        this.queryService = queryService;
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.officeDao = officeDao;
        this.addressDao = addressDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.fulfillmentOrderInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("fulfillment_orders")
                .usingGeneratedKeyColumns("id");
        fulfillmentOrderWithDetailExtractor = new FulfillmentOrderDaoImpl.FulfillmentOrderWithDetailExtractor();
        fulfillmentOrderGeneralStatisticExtractor = new FulfillmentOrderGeneralStatisticExtractor();
        fulfillmentOrderStatisticEmpExtractor = new FulfillmentOrderDaoImpl.FulfillmentOrderEmpStatisticExtractor();
        courierWayExtractor = new FulfillmentOrderDaoImpl.CourierWayExtractor();
    }

    @Override
    public FulfillmentOrder create(FulfillmentOrder fulfillmentOrder) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", fulfillmentOrder.getOrder().getId())
                .addValue("ccagent_id",
                        Objects.isNull(fulfillmentOrder.getCcagent()) ? null : fulfillmentOrder.getCcagent().getId())
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
    public List<FulfillmentOrder> findByCourier(Long courierId) {
        String findByStatusByCourierQuery = queryService.getQuery("fulfillment_order.findByCourier");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("courier_id", courierId);
        List<FulfillmentOrder> fulfillmentOrders = jdbcTemplate.query(findByStatusByCourierQuery, parameterSource, fulfillmentOrderWithDetailExtractor);
        return fulfillmentOrders.isEmpty() ? null : fulfillmentOrders;
    }

    @Override
    public List<FulfillmentOrder> findFulfillmentForCcagent(Long ccagentId){
        String findByStatusByCcagentQuery = queryService.getQuery("fulfillment_order.findByCcagent");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("ccagent_id", ccagentId);
        List<FulfillmentOrder> fulfillmentOrders = jdbcTemplate.query(findByStatusByCcagentQuery, parameterSource, fulfillmentOrderWithDetailExtractor);
        return fulfillmentOrders.isEmpty() ? null : fulfillmentOrders;
    }

    @Override
    public FulfillmentOrder findFulfillmentByOrder(Order order) {
        String findByOrder = queryService.getQuery("fulfillment_order.findByOrder");
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", order.getId());
        List<FulfillmentOrder> fulfillmentOrders = jdbcTemplate.query(findByOrder, parameterSource, fulfillmentOrderWithDetailExtractor);
        return fulfillmentOrders.isEmpty() ? null : fulfillmentOrders.get(0);
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

    @Override
    public List<CourierPoint> getCourierWay(Long courierId) {
        String findAllCourierPoints = queryService.getQuery("fulfillment_order.findAllCourierPoints");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("courier_id", courierId);

        List<CourierPoint> courierPoints = jdbcTemplate.query(findAllCourierPoints, parameterSource, courierWayExtractor);
        return courierPoints;
    }

    @Override
    public GeneralStatistic findCCAgentStatisticByCompany(String startDate, String endDate) {
        String findCCAgentStatisticByCompanyQuery = queryService.getQuery("fulfilment_order.avg_min_max_sum_by_ccagent");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <GeneralStatistic> generalStatistics = jdbcTemplate.query(findCCAgentStatisticByCompanyQuery, parameterSource, fulfillmentOrderGeneralStatisticExtractor);
        return generalStatistics.isEmpty() ? null : generalStatistics.get(0);
    }

    @Override
    public GeneralStatistic findCCAgentStatisticByManager(Long id, String startDate, String endDate) {
        String findCCAgentStatisticByManagerQuery = queryService.getQuery("fulfilment_order.avg_min_max_sum_by_ccagent_manager");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <GeneralStatistic> generalByManagerStatistics = jdbcTemplate.query(findCCAgentStatisticByManagerQuery, parameterSource, fulfillmentOrderGeneralStatisticExtractor);
        return generalByManagerStatistics.isEmpty() ? null : generalByManagerStatistics.get(0);
    }

    @Override
    public List <UserStatistic> findPersonalCCAgentStatisticByManager(Long id, String startDate, String endDate) {
        String findCCAgentStatisticByManagerQuery = queryService.getQuery("fulfilment_order.ccagentStat");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <UserStatistic> personalCategoryStatistics = jdbcTemplate.query(findCCAgentStatisticByManagerQuery, parameterSource, fulfillmentOrderStatisticEmpExtractor);
        return personalCategoryStatistics.isEmpty() ? null : personalCategoryStatistics;
    }

    @Override
    public GeneralStatistic findCourierStatisticByCompany(String startDate, String endDate) {
        String findCourierStatisticByCompanyQuery = queryService.getQuery("fulfilment_order.avg_min_max_sum_by_courier");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <GeneralStatistic> generalStatistics = jdbcTemplate.query(findCourierStatisticByCompanyQuery, parameterSource, fulfillmentOrderGeneralStatisticExtractor);
        return generalStatistics.isEmpty() ? null : generalStatistics.get(0);
    }

    @Override
    public GeneralStatistic findCourierStatisticByManager(Long id, String startDate, String endDate) {
        String findCourierStatisticByManagerQuery = queryService.getQuery("fulfilment_order.avg_min_max_sum_by_courier_manager");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <GeneralStatistic> generalByManagerStatistics = jdbcTemplate.query(findCourierStatisticByManagerQuery, parameterSource, fulfillmentOrderGeneralStatisticExtractor);
        return generalByManagerStatistics.isEmpty() ? null : generalByManagerStatistics.get(0);
    }

    @Override
    public List <UserStatistic> findPersonalCourierStatisticByManager(Long id, String startDate, String endDate) {
        String findCCAgentStatisticByManagerQuery = queryService.getQuery("fulfilment_order.courierStat");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("startDate", startDate, Types.DATE)
                .addValue("endDate", endDate, Types.DATE);
        List <UserStatistic> personalCategoryStatistics = jdbcTemplate.query(findCCAgentStatisticByManagerQuery, parameterSource, fulfillmentOrderStatisticEmpExtractor);
        return personalCategoryStatistics.isEmpty() ? null : personalCategoryStatistics;
    }

    @Override
    public Long findCountOrdersByCCagentInCurrentMonth(Long id) {
        String findCountOrdersByCCAgentQuery = queryService.getQuery("fulfilment_order.ordersByCCAgentInCurrentMonth");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(findCountOrdersByCCAgentQuery, parameterSource, Long.class);
    }

    @Override
    public Long findCountOrdersByCourierInCurrentMonth(Long id) {
        String findCountOrdersByCourierQuery = queryService.getQuery("fulfilment_order.ordersByCourierInCurrentMonth");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(findCountOrdersByCourierQuery, parameterSource, Long.class);
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

    private final class CourierWayExtractor implements ResultSetExtractor<List<CourierPoint>>, TimestampExtractor {

        @Override
        public List<CourierPoint> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<CourierPoint> courierPoints = new ArrayList<>();
            while (rs.next()) {
                CourierPoint courierPointFrom = new CourierPoint();
                CourierPoint courierPointTo = new CourierPoint();

                Long orderId = rs.getLong("o.id");
                if (orderId != 0) {
                    Order proxyOrder = new ProxyOrder(orderDao);
                    proxyOrder.setId(orderId);
                    courierPointFrom.setOrder(proxyOrder);
                    courierPointTo.setOrder(proxyOrder);


                    Long officeId = rs.getLong("o.office_id");
                    if (officeId != 0) {
                        Office office = new ProxyOffice(officeDao);
                        office.setId(officeId);
                        proxyOrder.setOffice(office);
                    }

                    Long senderAddressId = rs.getLong("o.sender_address_id");
                    if (senderAddressId != 0) {
                        Address senderAddress = new ProxyAddress(addressDao);
                        senderAddress.setId(senderAddressId);
                        proxyOrder.setSenderAddress(senderAddress);
                    }

                    Long receiverAddressId = rs.getLong("o.receiver_address_id");
                    if (receiverAddressId != 0) {
                        Address receiverAddress = new ProxyAddress(addressDao);
                        receiverAddress.setId(receiverAddressId);
                        proxyOrder.setReceiverAddress(receiverAddress);
                    }

                    proxyOrder.setCreationTime(getLocalDateTime(rs.getTimestamp("o.creation_time")));
                    proxyOrder.setExecutionTime(getLocalDateTime(rs.getTimestamp("o.execution_time")));

                    Long parentId = rs.getLong("o.parent_id");
                    if (parentId != 0) {
                        Order parentOrder = new ProxyOrder(orderDao);
                        parentOrder.setId(parentId);
                        proxyOrder.setParent(parentOrder);
                    }

                    proxyOrder.setFeedback(rs.getString("o.feedback"));
                    proxyOrder.setDescription(rs.getString("o.description"));


                    Long orderStatusId = rs.getLong("o.order_status_id");
                    if (orderStatusId != 0) {
                        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusId);
                        proxyOrder.setOrderStatus(orderStatus);
                    }

                    Long userId = rs.getLong("o.user_id");
                    if (userId != 0) {
                        User user = new ProxyUser(userDao);
                        user.setId(userId);
                        proxyOrder.setUser(user);
                    }

                }

                courierPointFrom.setOrderAction(TAKE);
                courierPointTo.setOrderAction(GIVE);

                courierPointFrom.setTime(getLocalDateTime(rs.getTimestamp("f.receiving_time")));
                courierPointTo.setTime(getLocalDateTime(rs.getTimestamp("f.shipping_time")));

                courierPoints.add(courierPointFrom);
                courierPoints.add(courierPointTo);

            }
            return courierPoints;
        }
    }

    private final class FulfillmentOrderGeneralStatisticExtractor implements ResultSetExtractor <List <GeneralStatistic>> {

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

    private final class FulfillmentOrderEmpStatisticExtractor implements ResultSetExtractor <List <UserStatistic>> {

        @Override
        public List <UserStatistic> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <UserStatistic> empCategoryStatistics = new ArrayList <>();
            while (rs.next()) {
                UserStatistic empCategoryStatistic = new UserStatistic();
                empCategoryStatistic.setId(rs.getLong("id"));
                empCategoryStatistic.setLastName(rs.getString("last_name"));
                empCategoryStatistic.setFirstName(rs.getString("first_name"));
                empCategoryStatistic.setCount(rs.getLong("orders"));
                empCategoryStatistic.setPercentageByCompany(rs.getDouble("per_company"));
                empCategoryStatistic.setPercentageByManager(rs.getDouble("per_manager"));
                empCategoryStatistic.setDifferenceBetweenAvgCompany(rs.getDouble("diff_company"));
                empCategoryStatistic.setDifferenceBetweenAvgManagerEmp(rs.getDouble("diff_manager"));

                empCategoryStatistics.add(empCategoryStatistic);
            }
            return empCategoryStatistics;
        }
    }

}

