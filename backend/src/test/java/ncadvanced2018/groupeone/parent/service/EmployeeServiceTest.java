package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOffice;
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
import java.util.List;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AddressDao addressDAO;

    @Test
    @Transactional
    @Rollback()
    public void createEmployeeTest() {
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

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        Assert.assertEquals(expectedLastName, resultEmployee.getLastName());
        Assert.assertEquals(expectedStreetAddress, resultAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback()
    public void updateEmployeeTest() {

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

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        String expectedStreetAddress = "TestingNew";
        String expectedLastName = "Test1New";

        resultAddress.setStreet(expectedStreetAddress);
        resultEmployee.setLastName(expectedLastName);

        employeeService.update(resultEmployee);

        User actualEmployee = employeeService.findById(resultEmployee.getId());
        Address actualAddress = actualEmployee.getAddress();

        Assert.assertEquals(expectedLastName, actualEmployee.getLastName());
        Assert.assertEquals(expectedStreetAddress, actualAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback()
    public void deleteOfficeTest() {
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

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        boolean isDeleted = employeeService.delete(resultEmployee);

        User actualEmployee = employeeService.findById(resultEmployee.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());

        Assert.assertEquals(null, actualEmployee);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
    }

    @Test
    @Transactional
    @Rollback()
    public void deleteOfficeByIdTest() {
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

        User resultEmployee = employeeService.create(employee);
        Address resultAddress = employee.getAddress();

        boolean isDeleted = employeeService.delete(resultEmployee.getId());

        User actualEmployee = employeeService.findById(resultEmployee.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());

        Assert.assertEquals(null, actualEmployee);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
    }

    @Test
    @Transactional
    @Rollback()
    public void findByNameTest() {

        Office office1 = new RealOffice();
        office1.setAddress(addressDAO.findById(1L));
        office1.setName("Test1");
        office1.setDescription("Testing");

        employeeService.create(office1);

        Office office2 = new RealOffice();
        office2.setAddress(addressDAO.findById(1L));
        office2.setName("Test2");
        office2.setDescription("Testing");

        employeeService.create(office2);

        List<Office> actualOffices = employeeService.findByName("Test");

        long actualSize = actualOffices.size();
        long sizeAfterFiltering = actualOffices.stream().filter(x -> x.getName().contains("Test")).count();

        Assert.assertEquals(actualSize, sizeAfterFiltering);
    }

    @Test
    @Transactional
    @Rollback()
    public void findByStreetTest() {

        Address address = new RealAddress();
        address.setStreet("TestingJUnit");
        address.setFloor(1);
        address.setHouse("29D");
        address.setFlat(44);

        Address realAddress = addressDAO.create(address);

        Office office1 = new RealOffice();
        office1.setAddress(realAddress);
        office1.setName("Test1");
        office1.setDescription("Testing");

        employeeService.create(office1);

        Office office2 = new RealOffice();
        office2.setAddress(realAddress);
        office2.setName("Test2");
        office2.setDescription("Testing");

        employeeService.create(office2);

        List<Office> actualOffices = employeeService.findByStreet("Test");

        long actualSize = actualOffices.size();
        long sizeAfterFiltering = actualOffices.stream().filter(x -> x.getAddress().getStreet().
                contains("Test")).count();
        Assert.assertEquals(actualSize, sizeAfterFiltering);
    }
}
