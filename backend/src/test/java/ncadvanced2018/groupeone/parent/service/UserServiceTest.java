package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.RoleDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
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
import java.util.HashSet;
import java.util.Set;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressDao addressDAO;
    @Autowired
    private RoleDao roleDAO;

    @Test
    @Transactional
    @Rollback
    public void createUserTest() {

        Set <Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.CLIENT);

        String expectedStreetAddress = "Testing";
        String expectedLastName = "Unitiv";

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet(expectedStreetAddress);

        User user = new RealUser();
        user.setAddress(address);
        user.setEmail("junit@service.mail");
        user.setLastName(expectedLastName);
        user.setFirstName("Junit");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(expectedRoles);

        User resultUser = userService.create(user);
        Address resultAddress = user.getAddress();

        Assert.assertEquals(expectedLastName, resultUser.getLastName());
        Assert.assertEquals(expectedStreetAddress, resultAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback
    public void createUserRolesTest() {

        Set <Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.UNVERIFIED_CLIENT);

        User user = new RealUser();
        user.setAddress(addressDAO.findById(1L));
        user.setEmail("junit@service.mail");
        user.setLastName("Vinnik");
        user.setFirstName("Vasya");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(expectedRoles);

        User resultUser = userService.create(user);
        Set <Role> actualRoles = roleDAO.findByUserId(resultUser.getId());

        Assert.assertTrue(actualRoles.containsAll(expectedRoles));
        Assert.assertEquals(1, actualRoles.size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateUserTest() {

        Set <Role> roles = new HashSet <>();
        roles.add(Role.VIP_CLIENT);

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        User user = new RealUser();
        user.setAddress(address);
        user.setEmail("junit@service.mail");
        user.setLastName("Unitiv");
        user.setFirstName("Junit");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(roles);

        User resultUser = userService.create(user);
        Address resultAddress = user.getAddress();

        String expectedStreetAddress = "TestingNew";
        String expectedLastName = "Test1New";
        Set <Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.CLIENT);

        resultAddress.setStreet(expectedStreetAddress);
        resultUser.setLastName(expectedLastName);
        resultUser.setRoles(expectedRoles);

        userService.update(resultUser);

        User actualUser = userService.findById(resultUser.getId());
        Address actualAddress = actualUser.getAddress();
        Set <Role> actualRoles = actualUser.getRoles();

        Assert.assertEquals(expectedLastName, actualUser.getLastName());
        Assert.assertEquals(expectedStreetAddress, actualAddress.getStreet());
        Assert.assertTrue(actualRoles.containsAll(expectedRoles));
        Assert.assertEquals(1, actualRoles.size());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserTest() {

        Set <Role> roles = new HashSet <>();
        roles.add(Role.CLIENT);

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        User user = new RealUser();
        user.setAddress(address);
        user.setEmail("junit@service.mail");
        user.setLastName("Unitiv");
        user.setFirstName("Junit");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(roles);

        User resultUser = userService.create(user);
        Address resultAddress = user.getAddress();

        boolean isDeleted = userService.delete(resultUser);

        User actualUser = userService.findById(resultUser.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());
        Set <Role> actualRoles = roleDAO.findByUserId(resultUser.getId());

        Assert.assertEquals(null, actualUser);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
        Assert.assertTrue(actualRoles.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserByIdTest() {
        Set <Role> roles = new HashSet <>();
        roles.add(Role.CLIENT);

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        User user = new RealUser();
        user.setAddress(address);
        user.setEmail("junit@service.mail");
        user.setLastName("Unitiv");
        user.setFirstName("Junit");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(roles);

        User resultUser = userService.create(user);
        Address resultAddress = user.getAddress();

        boolean isDeleted = userService.delete(resultUser.getId());

        User actualUser = userService.findById(resultUser.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());
        Set <Role> actualRoles = roleDAO.findByUserId(resultUser.getId());

        Assert.assertEquals(null, actualUser);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
        Assert.assertTrue(actualRoles.isEmpty());
    }

    @Test
    @Rollback
    @Transactional
    public void findByEmailTest() {
        String expectedEmail = "junit@service.mail";

        User user = new RealUser();
        user.setAddress(addressDAO.findById(1L));
        user.setEmail(expectedEmail);
        user.setLastName("Julinoza");
        user.setFirstName("Junit");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());

        userService.create(user);

        User actualUser = userService.findByEmail(expectedEmail);

        Assert.assertEquals(expectedEmail, actualUser.getEmail());
    }

}
