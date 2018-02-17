package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dao.ServiceDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.Service;
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
public class ServiceDaoImpl implements ServiceDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert serviceInsert;
    private ServiceWithDetailExtractor serviceWithDetailExtractor;
    private QueryService queryService;
    private UserDao userDao;
    private OrderDao orderDao;

    @Autowired
    public ServiceDaoImpl(QueryService queryService, UserDao userDao, OrderDao orderDao) {
        this.queryService = queryService;
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.serviceInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("services")
                .usingGeneratedKeyColumns("id");
        serviceWithDetailExtractor = new ServiceDaoImpl.ServiceWithDetailExtractor();
    }

    @Override
    public Service create(Service service) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", service.getOrder().getId())
                .addValue("ccagent_id", service.getCcagent().getId())
                .addValue("courier_id",
                        Objects.isNull(service.getCourier()) ? null : service.getCourier().getId())
                .addValue("confirmation_time",
                        Objects.isNull(service.getConfirmationTime()) ? null : Timestamp.valueOf(service.getConfirmationTime()))
                .addValue("shipping_time",
                        Objects.isNull(service.getShippingTime()) ? null : Timestamp.valueOf(service.getShippingTime()))
                .addValue("attempt", service.getAttempt());
        Long id = serviceInsert.executeAndReturnKey(parameterSource).longValue();
        service.setId(id);
        return service;
    }

    @Override
    public Service findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("service.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<Service> services = jdbcTemplate.query(findUserByIdQuery, parameterSource, serviceWithDetailExtractor);
        return services.isEmpty() ? null : services.get(0);
    }

    @Override
    public boolean update(Service service) {
        String update = queryService.getQuery("service.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", service.getId())
                .addValue("order_id", service.getOrder().getId())
                .addValue("ccagent_id", service.getCcagent().getId())
                .addValue("courier_id",
                        Objects.isNull(service.getCourier()) ? null : service.getCourier().getId())
                .addValue("confirmation_time",
                        Objects.isNull(service.getConfirmationTime()) ? null : Timestamp.valueOf(service.getConfirmationTime()))
                .addValue("shipping_time",
                        Objects.isNull(service.getShippingTime()) ? null : Timestamp.valueOf(service.getShippingTime()))
                .addValue("attempt", service.getAttempt());
        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Service service) {
        return delete(service.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("service.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class ServiceWithDetailExtractor implements ResultSetExtractor<List<Service>>,TimestampExtractor {

        @Override
        public List<Service> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Service> services = new ArrayList<>();
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getLong("id"));
                service.setOrder(orderDao.findById(rs.getLong("order_id")));
                service.setCcagent(userDao.findById(rs.getLong("ccagent_id")));
                service.setConfirmationTime(getLocalDateTime(rs.getTimestamp("confirmation_time")));
                service.setShippingTime(getLocalDateTime(rs.getTimestamp("shipping_time")));
                service.setAttempt(rs.getInt("attempt"));
                services.add(service);
            }
            return services;
        }
    }
}
