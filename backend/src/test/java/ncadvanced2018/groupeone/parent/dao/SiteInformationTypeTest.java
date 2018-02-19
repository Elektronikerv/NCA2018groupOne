package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformationType;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealSiteInformationType;
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
public class SiteInformationTypeTest {

    @Autowired
    private SiteInformationTypeDao siteInformationTypeDao;

    @Test
    @Transactional
    @Rollback
    public void insertSiteInformationTypeTest() {
        SiteInformationType expected = new RealSiteInformationType();
        expected.setName("Junit");

        siteInformationTypeDao.create(expected);
        SiteInformationType actual = siteInformationTypeDao.findById(expected.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void updateSiteInformationTypeTest() {
        SiteInformationType expected = siteInformationTypeDao.findById(3L);
        expected.setName("Junit");

        siteInformationTypeDao.update(expected);
        SiteInformationType actual = siteInformationTypeDao.findById(3L);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteSiteInformationTypeTest() {
        SiteInformationType expected = new RealSiteInformationType();
        expected.setName("Junit");

        SiteInformationType actual = siteInformationTypeDao.create(expected);
        siteInformationTypeDao.delete(actual);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void findSiteInformationTypeById() {
        Long expected = 2L;
        SiteInformationType actual = siteInformationTypeDao.findById(expected);

        log.info("Fetched siteInformationType by id: {}", actual.getId());
        Assert.assertEquals(expected, actual.getId());
    }

}
