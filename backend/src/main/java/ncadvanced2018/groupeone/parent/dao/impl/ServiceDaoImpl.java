package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dao.ServiceDao;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceDaoImpl implements ServiceDao {

    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert serviceInsert;
    private ServiceWithDetailExtractor serviceWithDetailExtractor;
    private QueryService queryService;


    public ServiceDaoImpl(QueryService queryService) {
        this.queryService = queryService;
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
    public Service create(Service entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", entity.getOrder())
                .addValue("ccagent_id", entity.getCcagent())
                .addValue("courier_id", entity.getCourier())
                .addValue("confirmation_time", entity.getConfirmationTime())
                .addValue("shipping_time", entity.getShippingTime())
                .addValue("attempt", entity.getAttempt());
        Long id = serviceInsert.executeAndReturnKey(parameterSource).longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public Service findById(Long id) {
        String findUserByIdQuery  = queryService.getQuery("service.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<Service> services = jdbcTemplate.query(findUserByIdQuery, parameterSource, serviceWithDetailExtractor);
        if (services.isEmpty()) {
            return null;
        }
        return services.get(0);
    }
    @Override
    public boolean update(Service entity) {
        String update = queryService.getQuery("service.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("order_id", entity.getOrder())
                .addValue("ccagent_id", entity.getCcagent())
                .addValue("courier_id", entity.getCourier())
                .addValue("confirmation_time", entity.getConfirmationTime())
                .addValue("shipping_time", entity.getShippingTime())
                .addValue("attempt", entity.getAttempt());

        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Service entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById  = queryService.getQuery("service.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private static final class ServiceWithDetailExtractor implements ResultSetExtractor<List<Service>> {

        @Autowired
        private UserDao userDao;

        @Autowired
        private OrderDao orderDao;

        @Override
        public List<Service> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Service> services = new ArrayList<>();
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getLong("id"));
                service.setOrder(orderDao.findById(rs.getLong("order_id")));
                service.setCcagent(userDao.findById(rs.getLong("ccagent_id")));
                service.setConfirmationTime(rs.getTimestamp("confirmation_time").toLocalDateTime());
                service.setShippingTime(rs.getTimestamp("shipping_time").toLocalDateTime());
                service.setAttempt(rs.getInt("attempt"));
                services.add(service);
            }
            return services;
        }
    }

}
