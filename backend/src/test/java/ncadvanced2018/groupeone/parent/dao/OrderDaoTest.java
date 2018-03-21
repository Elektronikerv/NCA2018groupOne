package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.OrderService;
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
import java.util.ArrayList;
import java.util.List;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrderStatusDao orderStatusDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private OfficeDao officeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    @Rollback
    public void insertOrderTest() {
        Order expected = new RealOrder();
        expected.setOrderStatus(orderStatusDao.findById(1L));
        expected.setSenderAddress(addressDao.findById(1L));
        expected.setReceiverAddress(addressDao.findById(1L));
        expected.setExecutionTime(LocalDateTime.now());
        expected.setFeedback("Some feedback");
        expected.setDescription("Description");
        expected.setOffice(officeDao.findById(1L));
        expected.setParent(orderDao.findById(1L));
        expected.setUser(userDao.findById(12L));
        expected.setCreationTime(LocalDateTime.now());

        orderDao.create(expected);
        Order actual = orderDao.findById(expected.getId());

        Assert.assertEquals(expected.getFeedback(), actual.getFeedback());
    }

    @Test
    @Transactional
    @Rollback
    public void insertOrderWithNullsTest() {
        Order expected = new RealOrder();
        expected.setOrderStatus(orderStatusDao.findById(1L));
        expected.setSenderAddress(addressDao.findById(1L));
        expected.setReceiverAddress(addressDao.findById(1L));
        expected.setCreationTime(LocalDateTime.now());
        expected.setUser(userDao.findById(12L));

        orderDao.create(expected);
        Order actual = orderDao.findById(expected.getId());

        Assert.assertEquals(expected.getSenderAddress().getId(), actual.getSenderAddress().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void updateOrderTest() {
        Order expected = orderDao.findById(10L);
        expected.setOrderStatus(orderStatusDao.findById(1L));
        expected.setSenderAddress(addressDao.findById(1L));
        expected.setReceiverAddress(addressDao.findById(1L));
        expected.setExecutionTime(LocalDateTime.now());
        expected.setFeedback("Some feedback");
        expected.setDescription("Description");
        expected.setOffice(officeDao.findById(1L));
        expected.setParent(orderDao.findById(1L));
        expected.setUser(userDao.findById(12L));
        expected.setCreationTime(LocalDateTime.now());

        orderDao.update(expected);
        Order actual = orderDao.findById(10L);

        Assert.assertEquals(expected.getUser().getId(), actual.getUser().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void updateOrderWithNullsTest() {
        Order expected = orderDao.findById(10L);
        expected.setOrderStatus(orderStatusDao.findById(1L));
        expected.setSenderAddress(addressDao.findById(1L));
        expected.setReceiverAddress(addressDao.findById(1L));
        expected.setCreationTime(LocalDateTime.now());
        expected.setUser(userDao.findById(12L));

        orderDao.update(expected);
        Order actual = orderDao.findById(10L);

        Assert.assertEquals(expected.getUser().getId(), actual.getUser().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteOrderTest() {
        Order expected = new RealOrder();
        expected.setOrderStatus(orderStatusDao.findById(1L));
        expected.setSenderAddress(addressDao.findById(1L));
        expected.setReceiverAddress(addressDao.findById(1L));
        expected.setExecutionTime(LocalDateTime.now());
        expected.setFeedback("Some feedback");
        expected.setDescription("Description");
        expected.setOffice(officeDao.findById(1L));
        expected.setParent(orderDao.findById(1L));
        expected.setUser(userDao.findById(12L));
        expected.setCreationTime(LocalDateTime.now());

        Order order = orderDao.create(expected);
        orderDao.delete(order);

        Assert.assertNull(orderDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findAllOrdersTest(){
        String expectedStreet = "Testing";
        String expectedStreetTwo = "Testing2";

        Address senderAddress = new RealAddress();
        senderAddress.setFlat(123);
        senderAddress.setHouse("1a");
        senderAddress.setFloor(1);
        senderAddress.setStreet(expectedStreet);

        Address receiverAddress = new RealAddress();
        receiverAddress.setFlat(321);
        receiverAddress.setHouse("2b");
        receiverAddress.setFloor(2);
        receiverAddress.setStreet(expectedStreetTwo);
        User user = new RealUser();
        user.setId(9L);
        OrderStatus status = OrderStatus.valueOf(1L);

        Order order = new RealOrder();
        order.setSenderAddress(senderAddress);
        order.setReceiverAddress(receiverAddress);
        order.setOrderStatus(status);
        order.setUser(user);

        orderService.create(order);

        List<Long> listId = new ArrayList<>();
        orderDao.findAllOrders()
                .forEach(orderI -> listId.add(orderI.getId()));


        Assert.assertTrue(listId.contains(order.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldFetchById() {
        Long expectedId = 11L;
        Order order = orderDao.findById(expectedId);

        log.info("Fetched order by id: {}", order);
        Assert.assertEquals(expectedId, order.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void findOrderForUser(){
        User userTest = new RealUser();
        userTest.setFirstName("Ivan");
        userTest.setLastName("Nice guy");
        userTest.setPassword("opopopop");
        userTest.setEmail("niceGuyEmail@gmail.com");
        userTest.setPhoneNumber("0506078105");

        User userIvan = userDao.create(userTest);

        Order ivanOrder = new RealOrder();
        ivanOrder.setOrderStatus(orderStatusDao.findById(1L));
        ivanOrder.setSenderAddress(addressDao.findById(1L));
        ivanOrder.setReceiverAddress(addressDao.findById(1L));
        ivanOrder.setExecutionTime(LocalDateTime.now());
        ivanOrder.setFeedback("Some feedback");
        ivanOrder.setDescription("Description");
        ivanOrder.setOffice(officeDao.findById(1L));
        ivanOrder.setParent(orderDao.findById(1L));
        ivanOrder.setUser(userIvan);
        ivanOrder.setCreationTime(LocalDateTime.now());

        Order order = orderDao.create(ivanOrder);

        Order orderForUser = orderDao.findOrderForUser(userIvan.getId(), order.getId());

        Assert.assertEquals(orderForUser.getId(), order.getId());

    }
}