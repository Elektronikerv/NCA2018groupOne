package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
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


@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressDao addressDao;


    @Test
    @Transactional
    @Rollback
    public void createTest() {
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

        Order resultOrder = orderService.create(order);
        Address resultReceiverAddress = resultOrder.getReceiverAddress();
        Address resultSenderAddress = resultOrder.getSenderAddress();
        Long resultUserId = resultOrder.getUser().getId();
        OrderStatus resultStatus = resultOrder.getOrderStatus();

        Assert.assertEquals(9L, resultUserId, 0);
        Assert.assertEquals(expectedStreet, resultSenderAddress.getStreet());
        Assert.assertEquals(expectedStreetTwo, resultReceiverAddress.getStreet());
        Assert.assertEquals("DRAFT", resultStatus.toString());
    }

    @Test
    @Transactional
    @Rollback
    public void findByIdTest() {
        //need to add autoincrement in DB
        long expectedId = 1L;
        Order resultOrder = orderService.findById(expectedId);

        Assert.assertEquals(expectedId, resultOrder.getId(), 0);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        //May be need to add other fields
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

        Order resultOrder = orderService.create(order);
        Address resultReceiverAddress = resultOrder.getReceiverAddress();
        Address resultSenderAddress = resultOrder.getSenderAddress();
        String expectedReceiverStreet = "TestReceiver";
        String expectedSenderStreet = "TestSender";

        resultSenderAddress.setStreet(expectedSenderStreet);
        resultReceiverAddress.setStreet(expectedReceiverStreet);

        Order actualOrder = orderService.update(resultOrder);
        Address actualResAddress = actualOrder.getReceiverAddress();
        Address actualSenAddress = actualOrder.getSenderAddress();

        Assert.assertEquals(expectedSenderStreet, actualSenAddress.getStreet());
        Assert.assertEquals(expectedReceiverStreet, actualResAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
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

        Order resultOrder = orderService.create(order);
        Address resultReceiverAddress = order.getReceiverAddress();
        Address resultSenderAddress = order.getSenderAddress();
        boolean isDeleted = orderService.delete(resultOrder);

        Order actualOrder = orderService.findById(resultOrder.getId());
        Address actualResAddress = addressDao.findById(resultReceiverAddress.getId());
        Address actualSenAddress = addressDao.findById(resultSenderAddress.getId());

        Assert.assertEquals(null, actualOrder);
        Assert.assertEquals(null, actualResAddress);
        Assert.assertEquals(null, actualSenAddress);

    }

    @Test
    @Transactional
    @Rollback
    public void deleteByIdTest() {

        Address senderAddress = new RealAddress();
        senderAddress.setFlat(123);
        senderAddress.setHouse("1a");
        senderAddress.setFloor(1);
        senderAddress.setStreet("SenderTest");

        Address receiverAddress = new RealAddress();
        receiverAddress.setFlat(321);
        receiverAddress.setHouse("2b");
        receiverAddress.setFloor(2);
        receiverAddress.setStreet("ReceiverTest");
        User user = new RealUser();
        user.setId(9L);
        OrderStatus status = OrderStatus.valueOf(1L);

        Order order = new RealOrder();
        order.setSenderAddress(senderAddress);
        order.setReceiverAddress(receiverAddress);
        order.setOrderStatus(status);
        order.setUser(user);

        Order resultOrder = orderService.create(order);
        Address resultReceiverAddress = resultOrder.getReceiverAddress();
        Address resultSenderAddress = resultOrder.getSenderAddress();
        boolean isDeleted = orderService.delete(resultOrder.getId());

        Order actualOrder = orderService.findById(resultOrder.getId());
        Address actualResAddress = addressDao.findById(resultReceiverAddress.getId());
        Address actualSenAddress = addressDao.findById(resultSenderAddress.getId());

        /*Need to fix address removing control*/

        Assert.assertEquals(null, actualOrder);
    }

}