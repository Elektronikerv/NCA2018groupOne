package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.entity.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;


public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addUser(User user) {
        String addQuery = "INSERT INTO user(user_pk, login, password, first_name, last_name, phone_number, email, manager)" +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(addQuery, user.getUserPK(), user.getLogin(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getManager());
    }

    @Override
    public User getUserByEmail(String email) {
        String getUserByIdQuery = "SELECT user_pk, login, password, first_name," +
                                    "last_name, phone_number, email, manager FROM user WHERE email=?";
        return jdbcTemplate.queryForObject(getUserByIdQuery, new Object[]{email}, new UserMapper());
    }
}
