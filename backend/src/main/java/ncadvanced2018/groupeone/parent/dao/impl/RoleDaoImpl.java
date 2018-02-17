package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.RoleDao;
import ncadvanced2018.groupeone.parent.model.entity.Role;
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
public class RoleDaoImpl implements RoleDao {
    private NamedParameterJdbcOperations jdbcTemplate;
    private SimpleJdbcInsert roleInsert;
    private RoleWithDetailExtractor roleWithDetailExtractor;
    private QueryService queryService;

    @Autowired
    public RoleDaoImpl(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.roleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("roles")
                .usingGeneratedKeyColumns("id");
        this.roleWithDetailExtractor = new RoleWithDetailExtractor();
    }

    @Override
    public Role create(Role role) {
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("name", role.getName())
                .addValue("description", role.getDescription());
        Long id = roleInsert.executeAndReturnKey(sqlParameters).longValue();
        role.setId(id);
        return role;
    }

    @Override
    public Role findById(Long id) {
        String findRoleByIdQuery = queryService.getQuery("role.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<Role> roles = jdbcTemplate.query(findRoleByIdQuery, parameterSource, roleWithDetailExtractor);
        return roles.isEmpty() ? null : roles.get(0);
    }

    @Override
    public boolean update(Role role) {
        String update = queryService.getQuery("role.update");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", role.getId())
                .addValue("name", role.getName())
                .addValue("description", role.getDescription());
        int updatedRows = jdbcTemplate.update(update, sqlParameters);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(Role role) {
        return delete(role.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("role.deleteById");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, mapSqlParameterSource);
        return deletedRows > 0;
    }

    private final class RoleWithDetailExtractor implements ResultSetExtractor<List<Role>> {

        @Override
        public List<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Role> roles = new ArrayList<>();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                roles.add(role);
            }
            return roles;
        }
    }
}
