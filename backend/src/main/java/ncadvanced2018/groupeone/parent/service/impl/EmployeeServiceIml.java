package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceIml implements EmployeeService {

    private UserDao userDao;
    private AddressDao addressDao;
    private RoleService roleService;

    @Autowired
    public EmployeeServiceIml(UserDao userDao, AddressDao addressDao, RoleService roleService) {
        this.userDao = userDao;
        this.addressDao = addressDao;
        this.roleService = roleService;
    }

    @Override
    public User create(User employee) {
        Assert.notNull(employee, "Employee object is null when creating");
        Address address = employee.getAddress();
        if (address != null) {
            address = addressDao.create(address);
            employee.setAddress(address);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encode);

        User createdEmployee = userDao.create(employee);
        log.debug("user roles: {}", employee.getRoles());
        if (employee.getRoles() != null) {
            employee.getRoles().forEach(x -> roleService.addRole(employee, x));
        }
        log.debug("user: {}", employee);
        return createdEmployee;
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
    public User update(User employee) {
        if (employee == null) {
            log.info("Employee object is null when updating");
            throw new EntityNotFoundException("Employee object is null");
        }
        if (userDao.findById(employee.getId()) == null) {
            log.info("No such employee entity");
            throw new NoSuchEntityException("Employee id is not found");
        }

        if (employee.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder.encode(employee.getPassword());
            employee.setPassword(encode);
        }

        Address address = employee.getAddress();
        addressDao.update(address);
        employee.setAddress(address);
        roleService.updateRoles(employee);
        return userDao.update(employee);
    }

    @Override
    public boolean delete(User employee) {
        if (employee == null) {
            log.info("Employee object is null when deleting");
            throw new EntityNotFoundException("Employee object is null");
        }
        if (employee.getRoles() != null) {
            new HashSet <>(employee.getRoles()).forEach(x -> roleService.deleteRole(employee, x));
        }
        Address address = employee.getAddress();
        boolean isDeleted = userDao.delete(employee);
        addressDao.delete(address);
        return isDeleted;
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        User employee = userDao.findById(id);
        if (userDao.findById(employee.getId()) == null) {
            log.info("No such office entity");
            throw new NoSuchEntityException("Office id is not found");
        }
        if (employee.getRoles() != null) {
            new HashSet <>(employee.getRoles()).forEach(x -> roleService.deleteRole(employee, x));
        }
        Address address = employee.getAddress();
        boolean isDeleted = userDao.delete(id);
        addressDao.delete(address);
        return isDeleted;
    }

    @Override
    public List <User> findByLastName(String lastName) {
        if (lastName == null) {
            log.info("Parameter is null when finding by last name");
            throw new IllegalArgumentException();
        }
        return userDao.findEmployeesByLastName(lastName);
    }

    @Override
    public List <User> findEmployeesByManager(User manager) {
        if (manager == null) {
            log.info("Parameter is null when finding by surname");
            throw new IllegalArgumentException();
        }
        return userDao.findEmployeesByManager(manager);
    }

    @Override
    public List <User> findAllEmployees() {
        return userDao.findAllEmployees();
    }

    @Override
    public List <User> findAllCouriers() {
        return userDao.findAllCouriers();
    }
}
