package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.entity.Address;
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
public class AddressDaoImpl implements AddressDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert addressInsert;
    private AddressWithDetailExtractor addressWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public AddressDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.addressInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("addresses")
                .usingGeneratedKeyColumns("id");
        addressWithDetailExtractor = new AddressWithDetailExtractor();
    }

    @Override
    public Address create(Address entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("street", entity.getStreet())
                .addValue("house", entity.getHouse())
                .addValue("floor", entity.getFloor())
                .addValue("flat", entity.getFlat());
        Long id = addressInsert.executeAndReturnKey(parameterSource).longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public Address findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("address.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<Address> addresses = jdbcTemplate.query(findUserByIdQuery, parameterSource, addressWithDetailExtractor);
        if (addresses.isEmpty()) {
            return null;
        }
        return addresses.get(0);
    }

    @Override
    public boolean update(Address entity) {
        String update = queryService.getQuery("address.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("street", entity.getStreet())
                .addValue("house", entity.getHouse())
                .addValue("floor", entity.getFloor())
                .addValue("flat", entity.getFlat());
        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Address entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("address.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class AddressWithDetailExtractor implements ResultSetExtractor<List<Address>> {

        @Override
        public List<Address> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Address> addresses = new ArrayList<>();
            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getLong("id"));
                address.setStreet(rs.getString("street"));
                address.setHouse(rs.getString("house"));
                address.setFloor(rs.getInt("floor"));
                address.setFlat((rs.getInt("flat")));
                addresses.add(address);
            }
            return addresses;
        }
    }

}
