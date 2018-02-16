package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.SiteInformationTypeDao;
import ncadvanced2018.groupeone.parent.entity.SiteInformationType;
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
public class SiteInformationTypeDaoImpl implements SiteInformationTypeDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert siteInformationTypeInsert;
    private SiteInformationTypeWithDetailExtractor siteInformationTypeWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public SiteInformationTypeDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.siteInformationTypeInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("site_information_types")
                .usingGeneratedKeyColumns("id");
        siteInformationTypeWithDetailExtractor = new SiteInformationTypeWithDetailExtractor();
    }

    @Override
    public SiteInformationType create(SiteInformationType entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", entity.getName());
        Long id = siteInformationTypeInsert.executeAndReturnKey(parameterSource).longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public SiteInformationType findById(Long id) {
        String findUserByIdQuery  = queryService.getQuery("site_information_type.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<SiteInformationType> siteInformationTypes = jdbcTemplate.query(findUserByIdQuery, parameterSource, siteInformationTypeWithDetailExtractor);
        if (siteInformationTypes.isEmpty()) {
            return null;
        }
        return siteInformationTypes.get(0);
    }
    @Override
    public boolean update(SiteInformationType entity) {
        String update = queryService.getQuery("site_information_type.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("name", entity.getName());

        int updatedRows = jdbcTemplate.update(update, parameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(SiteInformationType entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById  = queryService.getQuery("site_information_type.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private static final class SiteInformationTypeWithDetailExtractor implements ResultSetExtractor<List<SiteInformationType>> {

        @Override
        public List<SiteInformationType> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<SiteInformationType> siteInformationTypes = new ArrayList<>();
            while (rs.next()) {
                SiteInformationType siteInformationType = new SiteInformationType();
                siteInformationType.setId(rs.getLong("id"));
                siteInformationType.setName(rs.getString("name"));
                siteInformationTypes.add(siteInformationType);
            }
            return siteInformationTypes;
        }
    }

}

