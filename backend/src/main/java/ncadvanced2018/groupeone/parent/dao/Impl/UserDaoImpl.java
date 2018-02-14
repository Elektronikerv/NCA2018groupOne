package ncadvanced2018.groupeone.parent.dao.Impl;

import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.entity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private NamedParameterJdbcOperations jdbcTemplate;

    @Autowired
    public UserDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String addQuery = "INSERT INTO users(login, email, password, first_name, last_name, phone_number) VALUES (:login, :email,:password,:first_name,:last_name,:phone_number)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("login", user.getEmail());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("password", user.getPassword());
        mapSqlParameterSource.addValue("first_name", user.getFirstName());
        mapSqlParameterSource.addValue("last_name", user.getLastName());
        mapSqlParameterSource.addValue("phone_number", user.getPhoneNumber());
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(addQuery, mapSqlParameterSource, generatedKeyHolder, new String[]{"user_pk"});
        long id = generatedKeyHolder.getKey().longValue();
        user.setId(id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String getUserByIdQuery = "SELECT user_pk, login, password, first_name,last_name, phone_number, email, manager FROM users WHERE email=:email";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);
        return jdbcTemplate.queryForObject(getUserByIdQuery, mapSqlParameterSource, new UserMapper());
    }

    @Override
    public User getById(Long id) {
        String getUserByIdQuery = "SELECT user_pk, login, password, first_name,last_name, phone_number, email, manager FROM users WHERE user_pk=:user_pk";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_pk", id);
        return jdbcTemplate.queryForObject(getUserByIdQuery, mapSqlParameterSource, new UserMapper());
    }

    @Override
    public void deleteByEmail(String email) {
        String deleteByEmail = "DELETE FROM users WHERE email=:email";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);
        jdbcTemplate.update(deleteByEmail, mapSqlParameterSource);
    }

}