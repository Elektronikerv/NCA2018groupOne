package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.User;

public interface UserService {

    User create(User user);

    User findByEmail(String email);

    User findById(Long id);

    void deleteByEmail(String email);

}
