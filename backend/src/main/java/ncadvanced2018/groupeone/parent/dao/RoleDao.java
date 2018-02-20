package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.Role;

import java.util.Set;

public interface RoleDao extends CrudDao <Role, Long> {
    Set <Role> findByUserId(Long userId);
}
