package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.AdvertTypeDao;
import ncadvanced2018.groupeone.parent.model.entity.AdvertType;
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
public class AdvertTypeDaoImpl implements AdvertTypeDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert advertTypeInsert;
    private SiteInformationTypeWithDetailExtractor siteInformationTypeWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public AdvertTypeDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.advertTypeInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("advert_types")
                .usingGeneratedKeyColumns("id");
        siteInformationTypeWithDetailExtractor = new SiteInformationTypeWithDetailExtractor();
    }

    @Override
    public AdvertType create(AdvertType advertType) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", advertType.getName());
        Long id = advertTypeInsert.executeAndReturnKey(parameterSource).longValue();
        return null;
    }

    @Override
    public AdvertType findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("advert_type.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List <AdvertType> siteInformationTypes = jdbcTemplate.query(findUserByIdQuery, parameterSource, siteInformationTypeWithDetailExtractor);
        return siteInformationTypes.isEmpty() ? null : siteInformationTypes.get(0);
    }

    @Override
    public AdvertType update(AdvertType informationType) {
        String update = queryService.getQuery("advert_type.update");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", informationType.getId())
                .addValue("name", informationType.getName());

        jdbcTemplate.update(update, parameterSource);
        return informationType;
    }

    @Override
    public boolean delete(AdvertType informationType) {
        return delete(informationType.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("advert_type.deleteById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, parameterSource);
        return deletedRows > 0;
    }

    private final class SiteInformationTypeWithDetailExtractor implements ResultSetExtractor <List <AdvertType>> {

        @Override
        public List <AdvertType> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <AdvertType> siteInformationTypes = new ArrayList <>();
            while (rs.next()) {
                AdvertType siteInformationType = AdvertType.valueOf(rs.getLong("id"));
                siteInformationTypes.add(siteInformationType);
            }
            return siteInformationTypes;
        }
    }
}