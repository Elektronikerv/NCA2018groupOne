package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceIml implements EmployeeService {

    private UserDao userDao;
    private AddressDao addressDao;

    @Autowired
    public EmployeeServiceIml(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
        this.addressDao = addressDao;
    }

    @Override
    public User create(User employee) {
        //How to create?
        if (employee == null) {
            log.info("Employee object is null when creating");
            throw new EntityNotFoundException("Employee object is null");
        }
        Address address = employee.getAddress();
        if (address == null) {
            log.info("Address object is null when creating an employee");
            throw new EntityNotFoundException("Address object is null");
        }
        address = addressDao.create(address);
        employee.setAddress(address);
        return userDao.create(employee);
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
    public boolean update(User employee) {
        //How to update?
        if (employee == null) {
            log.info("Employee object is null when updating");
            throw new EntityNotFoundException("Employee object is null");
        }
        if (userDao.findById(employee.getId()) == null) {
            log.info("No such employee entity");
            throw new NoSuchEntityException("Employee id is not found");
        }
        Address address = employee.getAddress();
        //check?
        addressDao.update(address);
        employee.setAddress(address);
        return userDao.update(employee);
    }

    @Override
    public boolean delete(User employee) {
        //How to update?
        if (employee == null) {
            log.info("Employee object is null when deleting");
            throw new EntityNotFoundException("Employee object is null");
        }
        Address address = employee.getAddress();
        addressDao.delete(address);
        return userDao.delete(employee);
    }

    @Override
    public boolean delete(Long id) {
        //How to delete?
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        User employee = userDao.findById(id);
        if (userDao.findById(employee.getId()) == null) {
            log.info("No such office entity");
            throw new NoSuchEntityException("Office id is not found");
        }
        Address address = employee.getAddress();
        addressDao.delete(address);
        return userDao.delete(id);
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
}
