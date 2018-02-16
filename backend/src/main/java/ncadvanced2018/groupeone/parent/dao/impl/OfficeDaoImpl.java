package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.OfficeDao;
import ncadvanced2018.groupeone.parent.model.entity.Office;
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
public class OfficeDaoImpl implements OfficeDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert officeInsert;
    private OfficeWithDetailExtractor officeWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public OfficeDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.officeInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("offices")
                .usingGeneratedKeyColumns("id");
        officeWithDetailExtractor = new OfficeWithDetailExtractor();
    }

    @Override
    public Office create(Office entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("street", entity.getName())
                .addValue("house", entity.getAddress())
                .addValue("floor", entity.getDescription());
        Long id = officeInsert.executeAndReturnKey(parameterSource).longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public Office findById(Long id) {
        String findUserByIdQuery  = queryService.getQuery("office.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<Office> offices = jdbcTemplate.query(findUserByIdQuery, parameterSource, officeWithDetailExtractor);
        if (offices.isEmpty()) {
            return null;
        }
        return offices.get(0);
    }
    @Override
    public boolean update(Office entity) {
        String update = queryService.getQuery("office.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("street", entity.getName())
                .addValue("house", entity.getAddress())
                .addValue("floor", entity.getDescription());

        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Office entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById  = queryService.getQuery("office.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private static final class OfficeWithDetailExtractor implements ResultSetExtractor<List<Office>> {

        @Autowired
        private AddressDao addresDao;

        @Override
        public List<Office> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Office> offices = new ArrayList<>();
            while (rs.next()) {
                Office office = new Office();
                office.setId(rs.getLong("id"));
                office.setName(rs.getString("name"));
                office.setAddress(addresDao.findById(rs.getLong("address_id")));
                office.setDescription(rs.getString("description"));
                offices.add(office);
            }
            return offices;
        }
    }

}

