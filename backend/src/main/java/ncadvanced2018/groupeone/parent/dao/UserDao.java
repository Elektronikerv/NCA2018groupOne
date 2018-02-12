package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.entity.User;
import org.springframework.stereotype.Repository;
/**
 * Created by Vladyslav on 10.02.2018.
 */

public interface UserDao {
    User addUser(User user);
    User getUserByEmail(String email);
    User getById(Long id);
}
