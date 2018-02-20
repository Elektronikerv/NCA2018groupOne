package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;

public interface RoleService {
    boolean addRole(User user, Role role);

    boolean updateRoles(User user);

    boolean deleteRole(User user, Role role);
}
