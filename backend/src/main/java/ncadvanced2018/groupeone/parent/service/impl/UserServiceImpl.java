package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.RoleService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private AddressDao addressDao;
    private RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, AddressDao addressDao, RoleService roleService) {
        this.userDao = userDao;
        this.addressDao = addressDao;
        this.roleService = roleService;
    }

    @Override
    public User create(User user) {
        if (user == null) {
            log.info("User object is null when creating");
            throw new EntityNotFoundException("Employee object is null");
        }
        Address address = user.getAddress();
        if (address != null) {
            address = addressDao.create(address);
            user.setAddress(address);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        Set <Role> defaultRole = new HashSet <>();
        defaultRole.add(Role.UNVERIFIED_CLIENT);

        user.setRoles(defaultRole);

        User createdUser = userDao.create(user);
        user.getRoles().forEach(x -> roleService.addRole(createdUser, x));

        return createdUser;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            log.info("email String is null");
            throw new IllegalArgumentException();
        }
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return userDao.findById(id);
    }


    @Override
    public User update(User user) {
        if (user == null) {
            log.info("User object is null when updating");
            throw new EntityNotFoundException("User object is null");
        }
        if (userDao.findById(user.getId()) == null) {
            log.info("No such user entity");
            throw new NoSuchEntityException("User id is not found");
        }
        Address address = user.getAddress();
        if(address != null) {
            addressDao.update(address);
            user.setAddress(address);
        }
        roleService.updateRoles(user);
        return userDao.update(user);
    }

    @Override
    public boolean delete(User user) {
        if (user == null) {
            log.info("User object is null when deleting");
            throw new EntityNotFoundException("User object is null");
        }
        if (user.getRoles() != null) {
            new HashSet <>(user.getRoles()).forEach(x -> roleService.deleteRole(user, x));
        }
        Address address = user.getAddress();
        boolean isDeleted = userDao.delete(user);
        addressDao.delete(address);
        return isDeleted;
    }

    @Override
    public boolean delete(String email) {
        if (email == null) {
            log.info("Email value is null when deleting");
            throw new EntityNotFoundException("Email is null");
        }
        return userDao.deleteByEmail(email);
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        User user = userDao.findById(id);
        if (user == null) {
            log.info("No such user entity");
            throw new NoSuchEntityException("User id is not found");
        }
        if (user.getRoles() != null) {
            new HashSet <>(user.getRoles()).forEach(x -> roleService.deleteRole(user, x));
        }
        Address address = user.getAddress();
        boolean isDeleted = userDao.delete(user.getId());
        addressDao.delete(address);
        return isDeleted;
    }
}
