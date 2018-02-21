package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformation;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealSiteInformation;
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
public class SiteInformationTest {
    @Autowired
    private SiteInformationDao siteInformationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SiteInformationTypeDao siteInformationTypeDao;

    @Test
    @Transactional
    @Rollback
    public void insertSiteInformationTest() {
        SiteInformation expected = new RealSiteInformation();
        expected.setText("Junit");
        expected.setAdmin(userDao.findById(1L));
        expected.setType(siteInformationTypeDao.findById(1L));
        expected.setHeader("Junit");

        siteInformationDao.create(expected);
        SiteInformation actual = siteInformationDao.findById(expected.getId());

        Assert.assertEquals(expected.getText(), actual.getText());
    }

    @Test
    @Transactional
    @Rollback
    public void updateSiteInformationTest() {
        SiteInformation expected = siteInformationDao.findById(3L);
        expected.setText("Junit");
        expected.setAdmin(userDao.findById(1L));
        expected.setType(siteInformationTypeDao.findById(1L));

        siteInformationDao.update(expected);
        SiteInformation actual = siteInformationDao.findById(3L);

        Assert.assertEquals(expected.getText(), actual.getText());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteSiteInformationTest() {
        SiteInformation expected = new RealSiteInformation();
        expected.setText("Junit");
        expected.setAdmin(userDao.findById(1L));
        expected.setType(siteInformationTypeDao.findById(1L));
        expected.setHeader("Junit");
        SiteInformation actual = siteInformationDao.create(expected);
        siteInformationDao.delete(actual);

        Assert.assertNull(siteInformationDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findSiteInformationById() {
        Long expected = 4L;
        SiteInformation actual = siteInformationDao.findById(expected);

        log.info("Fetched siteInformation by id: {}", actual.getId());
        Assert.assertEquals(expected, actual.getId());
    }

}
