package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAdvert;
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
public class AdvertTest {
    @Autowired
    private AdvertDao advertDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AdvertTypeDao advertTypeDao;

    @Test
    @Transactional
    @Rollback
    public void createAdvertTest() {
        Advert expected = new RealAdvert();
        expected.setText("Junit");
        expected.setHeader("Junit");
        expected.setAdmin(userDao.findById(1L));
        expected.setType(advertTypeDao.findById(1L));
        expected.setHeader("Junit");
        expected.setDateOfPublishing(LocalDateTime.now());

        advertDao.create(expected);
        Advert actual = advertDao.findById(expected.getId());

        Assert.assertEquals(expected.getText(), actual.getText());
    }

    @Test
    @Transactional
    @Rollback
    public void updateAdvertTest() {
        Advert expected = advertDao.findById(3L);
        expected.setText("Junit");
        expected.setHeader("Junit");
        expected.setAdmin(userDao.findById(1L));
        expected.setType(advertTypeDao.findById(1L));

        advertDao.update(expected);
        Advert actual = advertDao.findById(3L);

        Assert.assertEquals(expected.getText(), actual.getText());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteAdvertTest() {
        Advert expected = new RealAdvert();
        expected.setText("Junit");
        expected.setHeader("Junit");
        expected.setAdmin(userDao.findById(1L));
        expected.setType(advertTypeDao.findById(1L));
        expected.setHeader("Junit");
        expected.setDateOfPublishing(LocalDateTime.now());
        Advert actual = advertDao.create(expected);
        advertDao.delete(actual);

        Assert.assertNull(advertDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findAdvertByIdTest() {
        Long expected = 4L;
        Advert actual = advertDao.findById(expected);

        log.info("Fetched siteInformation by id: {}", actual.getId());
        Assert.assertEquals(expected, actual.getId());
    }



}
