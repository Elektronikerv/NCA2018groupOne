package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.UserService;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void findRoleById() {
        Arrays.asList(Role.values())
                .forEach(expected -> {
                    Role actual = roleDao.findById(expected.getId());
                    log.info("Fetched role by id: {}", actual.getId());
                    Assert.assertEquals(expected, actual);
                });
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
        expected.setManager(userService.findById(6L));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        expected.setRoles(roles);
        userService.create(expected);

        Set<Role> actual =  roleDao.findByUserId(expected.getId());

        Assert.assertEquals(expected.getRoles(), actual);
    }
}