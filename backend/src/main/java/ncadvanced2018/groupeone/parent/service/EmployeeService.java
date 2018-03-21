package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.User;

import java.util.List;

public interface EmployeeService {
    User create(User employee);

    User findById(Long id);

    User update(User employee);

    boolean delete(User employee);

    boolean delete(Long id);

    List <User> findByLastName(String lastName);

    List <User> findEmployeesByManager(User manager);

    List <User> findAllEmployees();

    List<User> findAll(String sortedField, boolean asc);

    List <User> findAllCouriers();

    List<User> findAllFreeCouriers();
}
