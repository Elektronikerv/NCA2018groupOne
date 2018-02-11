package ncadvanced2018.groupeone.parent.dao.Impl;

import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.entity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String addQuery = "INSERT INTO user(user_pk, login, password, first_name, last_name, phone_number, email, manager)" +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(addQuery, user.getUserPK(), user.getLogin(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getManager());

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String getUserByIdQuery = "SELECT user_pk, login, password, first_name," +
                                    "last_name, phone_number, email, manager FROM user WHERE email=?";
        return jdbcTemplate.queryForObject(getUserByIdQuery, new Object[]{email}, new UserMapper());
    }
}
