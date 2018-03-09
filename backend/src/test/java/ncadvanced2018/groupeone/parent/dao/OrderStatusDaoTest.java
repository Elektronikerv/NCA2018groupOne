package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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
    public void shouldFetchById() {
//        Arrays.asList(OrderStatus.values())
//                .forEach(expected -> {
//                    OrderStatus actual = orderStatusDao.findById(expected.getId());
//                    log.info("Fetched order by id: {}", actual.getId());
//                    Assert.assertEquals(expected, actual);
//                });
    }
}