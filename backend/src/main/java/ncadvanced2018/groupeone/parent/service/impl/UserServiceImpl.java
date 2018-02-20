package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private AddressDao addressDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
        this.addressDao = addressDao;
    }

    @Override
    public User create(User user) {

        if (user == null) {
            log.info("User object is null when creating");
            throw new EntityNotFoundException("Employee object is null");
        }
        Address address = user.getAddress();
        if (address == null) {
            log.info("Address object is null when creating user");
            throw new EntityNotFoundException("Address object is null");
        }

        address = addressDao.create(address);
        user.setAddress(address);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        return userDao.create(user);
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
    public boolean update(User user) {
        if (user == null) {
            log.info("User object is null when updating");
            throw new EntityNotFoundException("User object is null");
        }
        if (userDao.findById(user.getId()) == null) {
            log.info("No such user entity");
            throw new NoSuchEntityException("User id is not found");
        }
        Address address = user.getAddress();
        addressDao.update(address);
        user.setAddress(address);
        return userDao.update(user);
    }

    @Override
    public boolean delete(User user) {
        if (user == null) {
            log.info("User object is null when deleting");
            throw new EntityNotFoundException("User object is null");
        }
        Address address = user.getAddress();
        addressDao.delete(address);
        return userDao.delete(user);
    }

    @Override
    public boolean delete(String email) {
        if(email == null){
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
        Address address = user.getAddress();
        addressDao.delete(address);
        return userDao.delete(id);
    }
}
