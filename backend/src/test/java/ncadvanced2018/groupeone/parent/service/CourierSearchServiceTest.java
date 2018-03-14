package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.model.entity.Order;
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

import java.util.HashSet;
import java.util.Set;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourierSearchServiceTest {

    @Autowired
    private CourierSearchService courierSearchService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void findCourierByOrderTest() {
//        Order order = orderService.findById(1L);
//
//        User courier1 = new RealUser();
//        courier1.setLastName("courierTest1");
//        courier1.setFirstName("courierTest1");
//
//        Set<Role> roles = new HashSet<Role>();
//        roles.add(Role.COURIER);
//
//        courier1.setRoles(roles);
//        courier1.setEmail("courierTest1@mail.com");
//        courier1.setPassword("courierTest1");
//        courier1.setPhoneNumber("38093123");
//        courier1.setCurrentPosition(addressDao.findById(1234L));
//
//        employeeService.create(courier1);
//
//        User courier2 = new RealUser();
//        courier2.setLastName("courierTest2");
//        courier2.setFirstName("courierTest2");
//
//        courier1.setRoles(roles);
//        courier1.setEmail("courierTest2@mail.com");
//        courier1.setPassword("courierTest2");
//        courier1.setPhoneNumber("380931234");
//        courier1.setCurrentPosition(addressDao.findById(2589L));
//
//        employeeService.create(courier2);
//
//        User courier3 = new RealUser();
//        courier3.setLastName("courierTest3");
//        courier3.setFirstName("courierTest3");
//
//        courier3.setRoles(roles);
//        courier3.setEmail("courierTest3@mail.com");
//        courier3.setPassword("courierTest3");
//        courier3.setPhoneNumber("380931235");
//        courier3.setCurrentPosition(addressDao.findById(543L));
//
//        employeeService.create(courier3);
//
//        User courier4 = new RealUser();
//        courier4.setLastName("courierTest4");
//        courier4.setFirstName("courierTest4");
//
//        courier4.setRoles(roles);
//        courier4.setEmail("courierTest4@mail.com");
//        courier4.setPassword("courierTest4");
//        courier4.setPhoneNumber("380931236");
//        courier4.setCurrentPosition(addressDao.findById(784L));
//
//        employeeService.create(courier4);
//
//        User user = courierSearchService.findCourierByOrder(order);
//
//        Assert.assertEquals(user.getId().longValue(), 543L);
    }
}
