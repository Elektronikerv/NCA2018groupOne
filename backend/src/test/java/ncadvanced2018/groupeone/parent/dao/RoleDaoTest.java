package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void findRoleById() {
        Long expected = 3L;
        Role roleById = roleDao.findById(expected);

        log.info("Fetched Role by id: {}", roleById);
        Assert.assertEquals(expected, roleById.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void findRoleByUserId() {
        User expected = new RealUser();
        expected.setEmail("junitEmail@gmail.com");
        expected.setFirstName("junitFirstName");
        expected.setLastName("junitLastName");
        expected.setPassword("junitPass");
        expected.setPhoneNumber("0506078105");
        expected.setRegistrationDate(LocalDateTime.now());
        expected.setAddress(addressDao.findById(10L));
        expected.setManager(userDao.findById(6L));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        expected.setRoles(roles);
        userDao.create(expected);
        expected.getRoles().forEach(x -> userDao.addRole(expected, x));
        Set<Role> actual =  roleDao.findByUserId(expected.getId());

        Assert.assertEquals(expected.getRoles(), actual);
    }
}