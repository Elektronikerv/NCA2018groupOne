package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealWorkingDay;
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
public class CourierServiceTest {
    @Autowired
    private CourierService courierService;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FulfillmentOrderDao fulfillmentOrderDao;
    @Autowired
    private WorkingDayService workingDayService;

    @Test
    @Transactional
    @Rollback
    public void courierAssignTest() {
//        Address senderAddress = addressDao.findById(1L);
//        Address receiverAddress = addressDao.findById(2L);
//        User ccagent = employeeService.findById(11L);
//
//        User employee1 = new RealUser();
//        employee1.setAddress(senderAddress);
//        employee1.setCurrentPosition(senderAddress);
//        employee1.setEmail("junit@service.mail");
//        employee1.setLastName("TestCourier");
//        employee1.setFirstName("TestCourier");
//        employee1.setPassword("123");
//        employee1.setPhoneNumber("0932781395");
//        employee1.setRegistrationDate(LocalDateTime.now());
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.COURIER);
//        employee1.setRoles(roles);
//
//        User expectedCourier = employeeService.create(employee1);
//        Long expectedCourierId = 31L;
//
//        WorkingDay workingDay = new RealWorkingDay();
//        workingDay.setUser(employee1);
//        workingDay.setWorkdayStart(LocalDateTime.now());
//        workingDay.setWorkdayEnd(LocalDateTime.now().plusDays(1));
//        WorkingDay createdWokingDay = workingDayService.create(workingDay);
//
//        Order order = new RealOrder();
//        order.setSenderAddress(senderAddress);
//        order.setReceiverAddress(receiverAddress);
//        order.setOrderStatus(OrderStatus.OPEN);
//        order.setUser(employee1);
//        order.setReceiverAvailabilityTimeFrom(LocalDateTime.now());
//        order.setReceiverAvailabilityTimeTo(LocalDateTime.now().plusDays(1));
//
//        Long orderId = orderService.create(order).getId();
//
//        order.setId(orderId);
//
//        FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findFulfillmentByOrder(order);
//
//        fulfillmentOrder.setCcagent(ccagent);
//
//        orderService.confirmFulfilmentOrder(fulfillmentOrder);
//
//        Long actualCourierId = fulfillmentOrderDao.findActualFulfillmentByOrder(order).getCourier().getId();
//
//        Assert.assertEquals(expectedCourierId, actualCourierId);
    }
}
