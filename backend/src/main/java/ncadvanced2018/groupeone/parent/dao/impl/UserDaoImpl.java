package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.entity.mapper.UserMapper;
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
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private NamedParameterJdbcOperations jdbcTemplate;

    private SimpleJdbcInsert userInsert;

    private UserWithDetailExtractor userWithDetailExtractor;

    public UserDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        userWithDetailExtractor = new UserWithDetailExtractor();
    }

    @Override
    public User create(User user) {
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("login", user.getEmail())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("phone_number", user.getPhoneNumber());
        Long newId = userInsert.executeAndReturnKey(sqlParameters).longValue();
        user.setId(newId);
        return user;

    }

    @Override
    public User findByEmail(String email) {
        String findUserByIdQuery = "SELECT id, login, password, first_name,last_name, phone_number, email, manager FROM users WHERE email=:email";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email);
        List<User> users = jdbcTemplate.query(findUserByIdQuery, params, userWithDetailExtractor);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        String findUserByIdQuery = "SELECT id, login, password, first_name,last_name, phone_number, email, manager FROM users WHERE id=:id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<User> users = jdbcTemplate.query(findUserByIdQuery, parameterSource, userWithDetailExtractor);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
    @Override
    public User deleteByEmail(String email) {
        if (email != null) {
            User user = findByEmail(email);
            String deleteByEmail = "DELETE FROM users WHERE email=:email";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("email", email);
            Long deletedRows = (long) jdbcTemplate.update(deleteByEmail, mapSqlParameterSource);
            if (deletedRows == 0) {
                return null;
            } else {
                return user;
            }
        }
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }

    private static final class UserWithDetailExtractor implements ResultSetExtractor<List<User>> {


        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new LinkedList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            return users;
        }
    }

}