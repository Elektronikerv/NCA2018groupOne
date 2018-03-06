package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
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

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FulfillmentOrderDaoTest {
    @Autowired
    private FulfillmentOrderDao fulfillmentOrderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;

//    @Test
//    @Transactional
//    @Rollback
//    public void insertServiceTest() {
//        FulfillmentOrder expected = new RealFulfillmentOrder();
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setCourier(null);
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setShippingTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        fulfillmentOrderDao.create(expected);
//        FulfillmentOrder actual = fulfillmentOrderDao.findById(expected.getId());
//
//        Assert.assertEquals(expected.getOrder().getId(), actual.getOrder().getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void insertServiceWithNullsTest() {
//        FulfillmentOrder expected = new RealFulfillmentOrder();
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        fulfillmentOrderDao.create(expected);
//        FulfillmentOrder actual = fulfillmentOrderDao.findById(expected.getId());
//
//        Assert.assertEquals(expected.getOrder().getId(), actual.getOrder().getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void updateServiceTest() {
//        FulfillmentOrder expected = fulfillmentOrderDao.findById(10L);
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setCourier(null);
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setShippingTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        fulfillmentOrderDao.update(expected);
//        FulfillmentOrder actual = fulfillmentOrderDao.findById(10L);
//
//        Assert.assertEquals(expected.getOrder().getId(), actual.getOrder().getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void deleteServiceTest() {
//        FulfillmentOrder expected = new RealFulfillmentOrder();
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setCourier(null);
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setShippingTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        FulfillmentOrder actual = fulfillmentOrderDao.create(expected);
//        fulfillmentOrderDao.delete(actual);
//
//        Assert.assertNull(fulfillmentOrderDao.findById(expected.getId()));
//    }

    @Test
    @Transactional
    @Rollback
    public void findServiceById() {
        Long expected = 11L;
//        FulfillmentOrder service = fulfillmentOrderDao.findById(expected);
//
//        log.info("Fetched service by id: {}", service.getId());
//        Assert.assertEquals(expected, service.getId());
    }
}