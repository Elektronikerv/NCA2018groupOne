package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private UserDao userDao;

    @Autowired
    public RoleServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean addRole(User user, Role role) {
        if (user == null || role == null) {
            log.info("Empty entity when adding role");
            throw new EntityNotFoundException();
        }
        return userDao.addRole(user, role);
    }

    @Override
    public boolean deleteRole(User user, Role role) {
        if (user == null || role == null) {
            log.info("Empty entity when adding role");
            throw new EntityNotFoundException();
        }
        return userDao.deleteRole(user, role);
    }

    @Override
    public boolean updateRoles(User userForUpdate) {

        User currentUser = userDao.findById(userForUpdate.getId());
        Set <Role> currentRoles = new HashSet <>(currentUser.getRoles());
        Set <Role> rolesForUpdate = new HashSet <>(userForUpdate.getRoles());

        for (Role currentRole : currentRoles) {
            if (!rolesForUpdate.contains(currentRole)) {
                userDao.deleteRole(currentUser, currentRole);
            }
        }

        for (Role roleForUpdate : rolesForUpdate) {
            if (!currentRoles.contains(roleForUpdate)) {
                userDao.addRole(currentUser, roleForUpdate);
            }
        }
        return false;
    }
}
