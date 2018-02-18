package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrderStatus;
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
public class OrderStatusDaoTest {

    @Autowired
    private OrderStatusDao orderStatusDao;

    @Test
    @Transactional
    @Rollback
    public void insertOrderStatusTest() {
        OrderStatus expected = new RealOrderStatus();
        expected.setName("Name");
        expected.setDescription("Description");

        orderStatusDao.create(expected);
        OrderStatus actual = orderStatusDao.findById(expected.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void updateOrderStatusTest() {
        OrderStatus expected = orderStatusDao.findById(5L);
        expected.setName("Name");
        expected.setDescription("Description");

        orderStatusDao.update(expected);
        OrderStatus actual = orderStatusDao.findById(5L);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteOrderStatusTest() {
        OrderStatus expected = new RealOrderStatus();
        expected.setName("Name");
        expected.setDescription("Description");

        OrderStatus order = orderStatusDao.create(expected);
        orderStatusDao.delete(order);

        Assert.assertNull(orderStatusDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldFetchById() {
        Long expectedId = 7L;
        OrderStatus order = orderStatusDao.findById(expectedId);

        log.info("Fetched order by id: {}", order.getId());
        Assert.assertEquals(expectedId, order.getId());
    }
}