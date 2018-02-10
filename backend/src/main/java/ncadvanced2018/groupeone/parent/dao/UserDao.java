package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.entity.User;
import org.springframework.stereotype.Repository;
/**
 * Created by Vladyslav on 10.02.2018.
 */

@Repository
public interface UserDao {
    void addUser(User user);
    User getUserByEmail(String email);
}
