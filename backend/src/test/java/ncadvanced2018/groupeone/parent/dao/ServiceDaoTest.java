package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Service;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealService;
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
public class ServiceDaoTest {
    @Autowired
    private ServiceDao serviceDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;

//    @Test
//    @Transactional
//    @Rollback
//    public void insertServiceTest() {
//        Service expected = new RealService();
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setCourier(null);
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setShippingTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        serviceDao.create(expected);
//        Service actual = serviceDao.findById(expected.getId());
//
//        Assert.assertEquals(expected.getConfirmationTime(), actual.getConfirmationTime());
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void insertServiceWithNullsTest() {
//        Service expected = new RealService();
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        serviceDao.create(expected);
//        Service actual = serviceDao.findById(expected.getId());
//
//        Assert.assertEquals(expected.getConfirmationTime(), actual.getConfirmationTime());
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void updateServiceTest() {
//        Service expected = serviceDao.findById(10L);
//        expected.setOrder(orderDao.findById(1L));
//        expected.setCcagent(userDao.findById(1L));
//        expected.setCourier(null);
//        expected.setConfirmationTime(LocalDateTime.now());
//        expected.setShippingTime(LocalDateTime.now());
//        expected.setAttempt(1);
//
//        serviceDao.update(expected);
//        Service actual = serviceDao.findById(10L);
//
//        Assert.assertEquals(expected.getConfirmationTime(), actual.getConfirmationTime());
//    }

    @Test
    @Transactional
    @Rollback
    public void deleteServiceTest() {
        Service expected = new RealService();
        expected.setOrder(orderDao.findById(1L));
        expected.setCcagent(userDao.findById(1L));
        expected.setCourier(null);
        expected.setConfirmationTime(LocalDateTime.now());
        expected.setShippingTime(LocalDateTime.now());
        expected.setAttempt(1);

        Service actual = serviceDao.create(expected);
        serviceDao.delete(actual);

        Assert.assertNull(serviceDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findServiceById() {
        Long expected = 11L;
        Service service = serviceDao.findById(expected);

        log.info("Fetched service by id: {}", service.getId());
        Assert.assertEquals(expected, service.getId());
    }
}