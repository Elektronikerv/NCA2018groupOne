package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.SiteInformationDao;
import ncadvanced2018.groupeone.parent.dao.SiteInformationTypeDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformation;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformationType;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealSiteInformation;
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
import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
public class SiteInformationDaoImpl implements SiteInformationDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert siteInformationInsert;
    private SiteInformationWithDetailExtractor siteInformationWithDetailExtractor;
    private QueryService queryService;
    private UserDao userDao;
    private SiteInformationTypeDao siteInformationTypeDao;

    @Autowired
    public SiteInformationDaoImpl(QueryService queryService, UserDao userDao, SiteInformationTypeDao siteInformationTypeDao) {
        this.queryService = queryService;
        this.userDao = userDao;
        this.siteInformationTypeDao = siteInformationTypeDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.siteInformationInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("site_information")
                .usingGeneratedKeyColumns("id");
        this.siteInformationWithDetailExtractor = new SiteInformationWithDetailExtractor();
    }

    @Override
    public SiteInformation create(SiteInformation siteInformation) {
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("text", siteInformation.getText())
                .addValue("admin_id", siteInformation.getAdmin().getId())
                .addValue("type_id", siteInformation.getType().getId())
                .addValue("header", siteInformation.getHeader());
        Long id = siteInformationInsert.executeAndReturnKey(sqlParameters).longValue();
        siteInformation.setId(id);
        return siteInformation;
    }

    @Override
    public SiteInformation findById(Long id) {
        String findSiteInformationByIdQuery = queryService.getQuery("siteInformation.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List <SiteInformation> siteInformations = jdbcTemplate.query(findSiteInformationByIdQuery, parameterSource, siteInformationWithDetailExtractor);
        return siteInformations.isEmpty() ? null : siteInformations.get(0);
    }

    @Override
    public SiteInformation update(SiteInformation siteInformation) {
        String updateQuery = queryService.getQuery("siteInformation.update");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", siteInformation.getId())
                .addValue("text", siteInformation.getText())
                .addValue("admin_id", siteInformation.getAdmin().getId())
<<<<<<< HEAD
                .addValue("type_id", siteInformation.getType().getId());
        jdbcTemplate.update(updateQuery, sqlParameters);
        return siteInformation;
=======
                .addValue("type_id", siteInformation.getType().getId())
                .addValue("header", siteInformation.getHeader());
        int updatedRows = jdbcTemplate.update(updateQuery, sqlParameters);
        return updatedRows > 0;
>>>>>>> adminFunctionalityOffices
    }

    @Override
    public boolean delete(SiteInformation siteInformation) {
        return delete(siteInformation.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("siteInformation.deleteById");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, mapSqlParameterSource);
        return deletedRows > 0;
    }

    private final class SiteInformationWithDetailExtractor implements ResultSetExtractor <List <SiteInformation>> {
        @Override
        public List <SiteInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <SiteInformation> siteInformations = new ArrayList <>();
            while (rs.next()) {
                SiteInformation siteInformation = new RealSiteInformation();
                siteInformation.setId(rs.getLong("id"));
                siteInformation.setText(rs.getString("text"));

                Long adminId = rs.getLong("admin_id");
                if (adminId != 0) {
                    User admin = new ProxyUser(userDao);
                    admin.setId(adminId);
                    siteInformation.setAdmin(admin);
                }

                Long typeId = rs.getLong("type_id");
                if (typeId != 0) {
                    SiteInformationType siteInformationType = SiteInformationType.valueOf(typeId);
                    siteInformation.setType(siteInformationType);
                }

                siteInformations.add(siteInformation);
            }
            return siteInformations;
        }
    }
}
    