package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.OrderStatusDao;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrderStatus;
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
public class OrderStatusDaoImpl implements OrderStatusDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert orderStatusInsert;
    private OrderStatusWithDetailExtractor addressWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.orderStatusInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("order_status")
                .usingGeneratedKeyColumns("id");
        addressWithDetailExtractor = new OrderStatusWithDetailExtractor();
    }

    @Override
    public OrderStatus create(OrderStatus orderStatus) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", orderStatus.getName())
                .addValue("description", orderStatus.getDescription());
        Long id = orderStatusInsert.executeAndReturnKey(parameterSource).longValue();
        orderStatus.setId(id);
        return orderStatus;
    }

    @Override
    public OrderStatus findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("order_status.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List <OrderStatus> orderStatuses = jdbcTemplate.query(findUserByIdQuery, parameterSource, addressWithDetailExtractor);
        return orderStatuses.isEmpty() ? null : orderStatuses.get(0);
    }

    @Override
    public boolean update(OrderStatus orderStatus) {
        String update = queryService.getQuery("order_status.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", orderStatus.getId())
                .addValue("name", orderStatus.getName())
                .addValue("description", orderStatus.getDescription());
        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(OrderStatus entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("order_status.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class OrderStatusWithDetailExtractor implements ResultSetExtractor <List <OrderStatus>> {

        @Override
        public List <OrderStatus> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <OrderStatus> orderStatuses = new ArrayList <>();
            while (rs.next()) {
                OrderStatus orderStatus = new RealOrderStatus();
                orderStatus.setId(rs.getLong("id"));
                orderStatus.setName(rs.getString("name"));
                orderStatus.setDescription(rs.getString("description"));
                orderStatuses.add(orderStatus);
            }
            return orderStatuses;
        }
    }
}