package ncadvanced2018.groupeone.parent.dao.impl;


import ncadvanced2018.groupeone.parent.dao.*;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.entity.Order;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert orderInsert;
    private OrderWithDetailExtractor orderWithDetailExtractor;
    private QueryService queryService;


    public OrderDaoImpl(QueryService queryService) {
        this.queryService = queryService;
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
    public Order create(Order entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("office_id", entity.getOffice())
                .addValue("sender_address_id", entity.getSenderAddress())
                .addValue("receiver_address_id", entity.getReceiverAddress())
                .addValue("creation_time", entity.getCreationTime())
                .addValue("execution_time", entity.getExecutionTime())
                .addValue("parent_id", entity.getParent())
                .addValue("user_id", entity.getUser())
                .addValue("description", entity.getDescription())
                .addValue("feedback", entity.getFeedback())
                .addValue("order_status_id", entity.getOrderStatus());
        Long id = orderInsert.executeAndReturnKey(parameterSource).longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public Order findById(Long id) {
        String findUserByIdQuery  = queryService.getQuery("order.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<Order> orders = jdbcTemplate.query(findUserByIdQuery, parameterSource, orderWithDetailExtractor);
        if (orders.isEmpty()) {
            return null;
        }
        return orders.get(0);
    }
    @Override
    public boolean update(Order entity) {
        String update = queryService.getQuery("order.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("office_id", entity.getOffice())
                .addValue("sender_address_id", entity.getSenderAddress())
                .addValue("receiver_address_id", entity.getReceiverAddress())
                .addValue("creation_time", entity.getCreationTime())
                .addValue("execution_time", entity.getExecutionTime())
                .addValue("parent_id", entity.getParent())
                .addValue("user_id", entity.getUser())
                .addValue("description", entity.getDescription())
                .addValue("feedback", entity.getFeedback())
                .addValue("order_status_id", entity.getOrderStatus());
        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Order entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById  = queryService.getQuery("order.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private static final class OrderWithDetailExtractor implements ResultSetExtractor<List<Order>> {

        @Autowired
        private UserDao userDao;

        @Autowired
        private OrderDao orderDao;

        @Autowired
        private OrderStatusDao orderStatusDao;

        @Autowired
        private AddressDao addresDao;

        @Autowired
        private OfficeDao officeDao;

        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOffice(officeDao.findById(rs.getLong("office_id")));
                order.setSenderAddress(addresDao.findById(rs.getLong("sender_address_id")));
                order.setReceiverAddress(addresDao.findById(rs.getLong("receiver_address_id")));
                order.setCreationTime(rs.getTimestamp("creation_time").toLocalDateTime());
                order.setExecutionTime(rs.getTimestamp("execution_time").toLocalDateTime());
                order.setParent(orderDao.findById(rs.getLong("parent_id")));
                order.setFeedback(rs.getString("feedback"));
                order.setDescription(rs.getString("description"));
                order.setOrderStatus(orderStatusDao.findById(rs.getLong("order_status_id")));
                order.setUser(userDao.findById(rs.getLong("user_id")));
                orders.add(order);
            }
            return orders;
        }
    }



}
