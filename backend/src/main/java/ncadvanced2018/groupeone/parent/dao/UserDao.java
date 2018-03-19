package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.dto.EmpProfile;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.util.List;


public interface UserDao extends CrudDao <User, Long> {
    User findByEmail(String email);

    boolean deleteByEmail(String email);

    boolean deleteRole(User user, Role role);

    boolean addRole(User user, Role role);

    List <User> findEmployeesByLastName(String lastName);

    List <User> findEmployeesByManager(User manager);

    List <User> findAllEmployees();

    List <User> findAllCouriers();

    User updateUserInfo(User user);

    User updatePassword(User user);

    User updateClientRoleToVIP(User user);

    User updateClientRoleToClient(User user);

    List <User> findAllFreeCouriers();

    List<User> findAllAvailableCouriers();

    List <EmpProfile> findEmployeesByManagerWithCounts(Long id);

    List <EmpProfile> findEmployeesByManagerAndLastNameWithCounts(Long id, String lastName);

    List<User> findAllManagers();

    User findManagerByEmployeeId(Long employeeId);

}
