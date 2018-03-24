package ncadvanced2018.groupeone.parent.dao.impl;

import lombok.NoArgsConstructor;
import ncadvanced2018.groupeone.parent.dao.AdvertDao;
import ncadvanced2018.groupeone.parent.dao.AdvertTypeDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.AdvertType;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAdvert;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@NoArgsConstructor
public class AdvertDaoImpl implements AdvertDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert advertInsert;
    private AdvertWithDetailExtractor advertWithDetailExtractor;
    private QueryService queryService;
    private UserDao userDao;
    private AdvertTypeDao advertTypeDao;

    @Autowired
    public AdvertDaoImpl(QueryService queryService, UserDao userDao, AdvertTypeDao advertTypeDao) {
        this.queryService = queryService;
        this.userDao = userDao;
        this.advertTypeDao = advertTypeDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.advertInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("adverts")
                .usingGeneratedKeyColumns("id");
        this.advertWithDetailExtractor = new AdvertWithDetailExtractor();
    }

    @Override
    public Advert create(Advert advert) {
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("header", advert.getHeader())
                .addValue("text", advert.getText())
                .addValue("admin_id", advert.getAdmin().getId())
                .addValue("type_id", advert.getType().getId())
                .addValue("date_of_publishing",
                        Objects.isNull(advert.getDateOfPublishing()) ?
                                Timestamp.valueOf(LocalDateTime.now()):
                                Timestamp.valueOf(advert.getDateOfPublishing()));
        Long id = advertInsert.executeAndReturnKey(sqlParameters).longValue();
        advert.setId(id);
        return advert;
    }

    @Override
    public Advert findById(Long id) {
        String findSiteInformationByIdQuery = queryService.getQuery("adverts.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List <Advert> adverts = jdbcTemplate.query(findSiteInformationByIdQuery, parameterSource, advertWithDetailExtractor);
        return adverts.isEmpty() ? null : adverts.get(0);
    }

    @Override
    public List <Advert> findAll() {
        String findAllQuery = queryService.getQuery("adverts.findAll");
        List <Advert> adverts = jdbcTemplate.query(findAllQuery, advertWithDetailExtractor);
        return adverts.isEmpty() ? null : adverts;
    }

    @Override
    public List<Advert> findAllSortedBy(String orderBy) {
        String findAllSortedByQuery = queryService.getQuery("adverts.findAll.orderBy") + orderBy;
        List <Advert> adverts = jdbcTemplate.query(findAllSortedByQuery, advertWithDetailExtractor);
        return adverts.isEmpty() ? null : adverts;
    }

    @Override
    public List<Advert> findAdvertsWithType(Long id) {
        String findAdvertsWithTypeQuery = queryService.getQuery("adverts.findAdvertsWithType");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("type_id", id);
        List <Advert> adverts = jdbcTemplate.query(findAdvertsWithTypeQuery, parameterSource, advertWithDetailExtractor);
        return adverts.isEmpty() ? null : adverts;
    }

    @Override
    public Advert update(Advert advert) {
        String updateQuery = queryService.getQuery("adverts.update");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", advert.getId())
                .addValue("text", advert.getText())
                .addValue("admin_id", advert.getAdmin().getId())
                .addValue("type_id", advert.getType().getId())
                .addValue("header", advert.getHeader())
                .addValue("date_of_publishing",
                        Objects.isNull(advert.getDateOfPublishing()) ?
                                Timestamp.valueOf(LocalDateTime.now()):
                                Timestamp.valueOf(advert.getDateOfPublishing()));
        int updatedRows = jdbcTemplate.update(updateQuery, sqlParameters);
        return findById(advert.getId());
    }

    @Override
    public boolean delete(Advert advert) {
        return delete(advert.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("adverts.deleteById");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, mapSqlParameterSource);
        return deletedRows > 0;
    }

    private final class AdvertWithDetailExtractor implements ResultSetExtractor <List <Advert>>, TimestampExtractor {
        @Override
        public List <Advert> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List <Advert> adverts = new ArrayList <>();
            while (rs.next()) {
                Advert advert = new RealAdvert();
                advert.setId(rs.getLong("id"));
                advert.setText(rs.getString("text"));
                advert.setHeader(rs.getString("header"));
                advert.setDateOfPublishing(getLocalDateTime(rs.getTimestamp("date_of_publishing")));
                Long adminId = rs.getLong("admin_id");
                if (adminId != 0) {
                    User admin = new ProxyUser(userDao);
                    admin.setId(adminId);
                    advert.setAdmin(admin);
                }

                Long typeId = rs.getLong("type_id");
                if (typeId != 0) {
                    AdvertType advertType = AdvertType.valueOf(typeId);
                    advert.setType(advertType);
                }

                adverts.add(advert);
            }
            return adverts;
        }
    }
}
    