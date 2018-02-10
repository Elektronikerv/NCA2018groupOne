package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addUser(User user) {
    
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}
