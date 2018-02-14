package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.entity.User;

public interface UserService {

    User addUser(User user);
    User getUserByEmail(String email);
    User getById(Long id);
    void deleteByEmail(String email);

}
