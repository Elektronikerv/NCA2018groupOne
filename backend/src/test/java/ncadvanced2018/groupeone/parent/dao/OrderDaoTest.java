package ncadvanced2018.groupeone.parent.dao;

import javafx.scene.effect.Reflection;
import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        Assert.assertEquals(expected.getCreationTime(), actual.getCreationTime());
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

        Assert.assertEquals(expected.getCreationTime(), actual.getCreationTime());
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

        Assert.assertEquals(expected.getCreationTime(), actual.getCreationTime());
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

        Assert.assertEquals(expected.getCreationTime(), actual.getCreationTime());
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
    public void shouldFetchById() {
        Long expectedId = 11L;
        Order order = orderDao.findById(expectedId);

        log.info("Fetched order by id: {}", order);
        Assert.assertEquals(expectedId, order.getId());
    }
}