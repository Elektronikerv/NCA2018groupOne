package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.util.List;


public interface UserDao extends CrudDao<User, Long> {
    User findByEmail(String email);

    boolean deleteByEmail(String email);

    boolean deleteRole(User user, Role role);

    boolean addRole(User user, Role role);

    List<User> findEmployeesByLastName(String lastName);

    List<User> findEmployeesByManager(User manager);

    List<User> findAllEmployees();
}
