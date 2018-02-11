package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.CrudDao;
import ncadvanced2018.groupeone.parent.entity.AbstractEntity;
import ncadvanced2018.groupeone.parent.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Basic implementation of {@link CrudDao} interface.
 *
 * @param <T> entity type.
 */
@Repository
public abstract class CrudDaoImpl<T extends AbstractEntity> implements CrudDao<T, Long> {

    private final NamedParameterJdbcOperations jdbcOperations;

    private final QueryService queryService;

    @Autowired
    public CrudDaoImpl(NamedParameterJdbcOperations jdbcOperations, QueryService queryService) {
        this.jdbcOperations = jdbcOperations;
        this.queryService = queryService;
    }

    @Override
    public T save(T entity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        String insertQuery = this.getInsertQuery();
        if (entity.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbcOperations.update(insertQuery, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            entity.setId(generatedId);
        } else {
            this.jdbcOperations.update(insertQuery, sqlParameterSource);
        }
        return entity;
    }

    @Override
    public T findOne(Long id) {
        String findOneQuery = this.getFindOneQuery();
        return jdbcOperations.queryForObject(findOneQuery,
                new MapSqlParameterSource("id", id),
                this.getMapper());
    }

    @Override
    public void delete(T entity) {
        this.delete(entity.getId());
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = this.getDeleteQuery();
        this.jdbcOperations.update(deleteQuery,
                new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean exists(Long id) {
        String existQuery = this.getExistQuery();
        return this.jdbcOperations.queryForObject(existQuery,
                new MapSqlParameterSource("id", id),
                Long.class) > 0;
    }

    @Override
    public Long count() {
        String findCountQuery = getCountQuery();
        return this.jdbcOperations.queryForObject(findCountQuery, new MapSqlParameterSource(), Long.class);
    }

    NamedParameterJdbcOperations jdbcOperations() {
        return this.jdbcOperations;
    }

    QueryService queryService() {
        return this.queryService;
    }

    abstract RowMapper<T> getMapper();

    abstract String getInsertQuery();

    abstract String getFindOneQuery();

    abstract String getDeleteQuery();

    abstract String getExistQuery();

    abstract String getCountQuery();
}