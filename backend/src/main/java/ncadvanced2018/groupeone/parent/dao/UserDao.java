package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.entity.User;


public interface UserDao extends CrudDao<User, Long> {
    User findByEmail(String email);
    boolean deleteByEmail(String email);
}
