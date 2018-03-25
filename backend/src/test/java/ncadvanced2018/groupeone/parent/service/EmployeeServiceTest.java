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
import java.util.List;
import java.util.Set;


@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AddressDao addressDAO;
    @Autowired
    private RoleDao roleDAO;

    @Test
    @Transactional
    @Rollback
    public void createEmployeeTest() {

        Set <Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.MANAGER);
        expectedRoles.add(Role.COURIER);

        String expectedStreetAddress = "Testing";
        String expectedLastName = "Unitiv";

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet(expectedStreetAddress);

        User employee = new RealUser();
        employee.setAddress(address);
        employee.setEmail("junit@service.mail");
        employee.setLastName(expectedLastName);
        employee.setFirstName("Junit");
        employee.setPassword("123");
        employee.setPhoneNumber("0932781395");
        employee.setRegistrationDate(LocalDateTime.now());
        employee.setRoles(expectedRoles);

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        Assert.assertEquals(expectedLastName, resultEmployee.getLastName());
        Assert.assertEquals(expectedStreetAddress, resultAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback
    public void createEmployeeRolesTest() {

        Set <Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.MANAGER);
        expectedRoles.add(Role.COURIER);

        User employee = new RealUser();
        employee.setAddress(addressDAO.findById(1L));
        employee.setEmail("junit@service.mail");
        employee.setLastName("Vinnik");
        employee.setFirstName("Vasya");
        employee.setPassword("123");
        employee.setPhoneNumber("0932781395");
        employee.setRegistrationDate(LocalDateTime.now());
        employee.setRoles(expectedRoles);

        User resultEmployee = employeeService.create(employee);
        Set <Role> actualRoles = roleDAO.findByUserId(resultEmployee.getId());

        Assert.assertTrue(actualRoles.containsAll(expectedRoles));
        Assert.assertEquals(expectedRoles.size(), actualRoles.size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateEmployeeTest() {

        Set <Role> roles = new HashSet <>();
        roles.add(Role.MANAGER);
        roles.add(Role.COURIER);

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        User employee = new RealUser();
        employee.setAddress(address);
        employee.setEmail("junit@service.mail");
        employee.setLastName("Unitiv");
        employee.setFirstName("Junit");
        employee.setPassword("123");
        employee.setPhoneNumber("0932781395");
        employee.setRegistrationDate(LocalDateTime.now());
        employee.setRoles(roles);

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        String expectedStreetAddress = "TestingNew";
        String expectedLastName = "Test1New";
        Set <Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.MANAGER);
        expectedRoles.add(Role.ADMIN);


        resultAddress.setStreet(expectedStreetAddress);
        resultEmployee.setLastName(expectedLastName);
        resultEmployee.setRoles(expectedRoles);

        employeeService.update(resultEmployee);

        User actualEmployee = employeeService.findById(resultEmployee.getId());
        Address actualAddress = actualEmployee.getAddress();
        Set <Role> actualRoles = actualEmployee.getRoles();

        Assert.assertEquals(expectedLastName, actualEmployee.getLastName());
        Assert.assertEquals(expectedStreetAddress, actualAddress.getStreet());
        Assert.assertTrue(actualRoles.containsAll(expectedRoles));
        Assert.assertEquals(expectedRoles.size(), actualRoles.size());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteEmployeeTest() {

        Set <Role> roles = new HashSet <>();
        roles.add(Role.MANAGER);
        roles.add(Role.COURIER);

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        User employee = new RealUser();
        employee.setAddress(address);
        employee.setEmail("junit@service.mail");
        employee.setLastName("Unitiv");
        employee.setFirstName("Junit");
        employee.setPassword("123");
        employee.setPhoneNumber("0932781395");
        employee.setRegistrationDate(LocalDateTime.now());
        employee.setRoles(roles);

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        boolean isDeleted = employeeService.delete(resultEmployee);

        User actualEmployee = employeeService.findById(resultEmployee.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());
        Set <Role> actualRoles = roleDAO.findByUserId(resultEmployee.getId());

        Assert.assertEquals(null, actualEmployee);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
        Assert.assertTrue(actualRoles.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteEmployeeByIdTest() {
        Set <Role> roles = new HashSet <>();
        roles.add(Role.MANAGER);
        roles.add(Role.COURIER);

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        User employee = new RealUser();
        employee.setAddress(address);
        employee.setEmail("junit@service.mail");
        employee.setLastName("Unitiv");
        employee.setFirstName("Junit");
        employee.setPassword("123");
        employee.setPhoneNumber("0932781395");
        employee.setRegistrationDate(LocalDateTime.now());
        employee.setRoles(roles);

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        boolean isDeleted = employeeService.delete(resultEmployee.getId());

        User actualEmployee = employeeService.findById(resultEmployee.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());
        Set <Role> actualRoles = roleDAO.findByUserId(resultEmployee.getId());

        Assert.assertEquals(null, actualEmployee);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
        Assert.assertTrue(actualRoles.isEmpty());
    }

//    @Test
//    @Rollback
//    @Transactional
//    public void findByLastNameTest() {
//
//        User employee1 = new RealUser();
//        employee1.setAddress(addressDAO.findById(1L));
//        employee1.setEmail("junit@service.mail");
//        employee1.setLastName("Julinoza");
//        employee1.setFirstName("Junit");
//        employee1.setPassword("123");
//        employee1.setPhoneNumber("0932781395");
//        employee1.setRegistrationDate(LocalDateTime.now());
//
//
//        employeeService.create(employee1);
//
//        User employee2 = new RealUser();
//        employee2.setAddress(addressDAO.findById(1L));
//        employee2.setEmail("junit@service1.mail");
//        employee2.setLastName("Junior");
//        employee2.setFirstName("Junit");
//        employee2.setPassword("123");
//        employee2.setPhoneNumber("0932781395");
//        employee2.setRegistrationDate(LocalDateTime.now());
//
//        employeeService.create(employee2);
//
//        List <User> actualEmployees = employeeService.findByLastName("Jun");
//
//        long actualSize = actualEmployees.size();
//        long sizeAfterFiltering = actualEmployees.stream().filter(x -> x.getLastName().contains("Jun")).count();
//
//        Assert.assertEquals(actualSize, sizeAfterFiltering);
//    }

    @Test
    @Transactional
    @Rollback
    public void findByManagerTest() {

        Set <Role> managerRoles = new HashSet <>();
        managerRoles.add(Role.MANAGER);

        Set <Role> employeesRoles = new HashSet <>();
        employeesRoles.add(Role.ADMIN);

        User manager = new RealUser();
        manager.setLastName("Junit");
        manager.setFirstName("Test");
        manager.setRegistrationDate(LocalDateTime.now());
        manager.setPhoneNumber("12345698");
        manager.setEmail("email@mail.gmail");
        manager.setAddress(addressDAO.findById(1L));
        manager.setRoles(managerRoles);
        manager.setPassword("789");

        User resultManager = employeeService.create(manager);

        User employee1 = new RealUser();
        employee1.setAddress(addressDAO.findById(1L));
        employee1.setEmail("junit@service1.mail");
        employee1.setLastName("Unitiv");
        employee1.setFirstName("Junit");
        employee1.setPassword("123");
        employee1.setPhoneNumber("0932781395");
        employee1.setRegistrationDate(LocalDateTime.now());
        employee1.setManager(resultManager);
        employee1.setRoles(employeesRoles);

        employeeService.create(employee1);

        User employee2 = new RealUser();
        employee2.setAddress(addressDAO.findById(1L));
        employee2.setEmail("junit@service.mail");
        employee2.setLastName("Unit");
        employee2.setFirstName("Junit");
        employee2.setPassword("123");
        employee2.setPhoneNumber("0942781395");
        employee2.setRegistrationDate(LocalDateTime.now());
        employee2.setManager(resultManager);
        employee2.setRoles(employeesRoles);

        employeeService.create(employee2);

        List <User> actualEmployees = employeeService.findEmployeesByManager(resultManager);

        long actualSize = actualEmployees.size(),
                expectedSize = 2;
        Assert.assertEquals(expectedSize, actualSize);
    }
}
