package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.RoleDao;
import ncadvanced2018.groupeone.parent.dao.SiteInformationDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealSiteInformation;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SiteInformationServiceTest {

    @Autowired
    private SiteInformationService siteInformationService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void createsSiteInformationTest() {
        Set<Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.ADMIN);

        String expectedText = "HELLO KITTY";

        User admin = new RealUser();
        admin.setAddress(addressDao.findById(1L));
        admin.setEmail("junit@service.mail");
        admin.setLastName("Junior");
        admin.setFirstName("Junit");
        admin.setPassword("123");
        admin.setPhoneNumber("0932781395");
        admin.setRegistrationDate(LocalDateTime.now());
        admin.setRoles(expectedRoles);

        User resultAdmin = userDao.create(admin);

        SiteInformation siteInformation = new RealSiteInformation();
        siteInformation.setAdmin(resultAdmin);
        siteInformation.setText(expectedText);
        siteInformation.setType(SiteInformationType.ADVERTISEMENT);
        siteInformation.setHeader("NCADV");

        SiteInformation siteInformationActual = siteInformationService.create(siteInformation);

        Assert.assertEquals(expectedText, siteInformationActual.getText());

    }

    @Test
    @Transactional
    @Rollback
    public void updateSiteInformationTest() {

        Set<Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.ADMIN);

        String actualText = "HELLO KITTY";

        User admin = new RealUser();
        admin.setAddress(addressDao.findById(1L));
        admin.setEmail("junit@service.mail");
        admin.setLastName("Junior");
        admin.setFirstName("Junit");
        admin.setPassword("123");
        admin.setPhoneNumber("0932781395");
        admin.setRegistrationDate(LocalDateTime.now());
        admin.setRoles(expectedRoles);

        User createdAdmin = userDao.create(admin);

        SiteInformation siteInformation = new RealSiteInformation();
        siteInformation.setAdmin(createdAdmin);
        siteInformation.setText(actualText);
        siteInformation.setType(SiteInformationType.ADVERTISEMENT);
        siteInformation.setHeader("NCADV");

        SiteInformation resultSiteInformation = siteInformationService.create(siteInformation);

        String expectedText = "TestingNew";
        resultSiteInformation.setText(expectedText);
        siteInformationService.update(resultSiteInformation);

        SiteInformation actualSiteInformation = siteInformationService.findById(resultSiteInformation.getId());
        Assert.assertEquals(expectedText, actualSiteInformation.getText());

    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserTest() {
        Set<Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.ADMIN);

        User admin = new RealUser();
        admin.setAddress(addressDao.findById(1L));
        admin.setEmail("junit@service.mail");
        admin.setLastName("Vasya");
        admin.setFirstName("Junit");
        admin.setPassword("123");
        admin.setPhoneNumber("0932781395");
        admin.setRegistrationDate(LocalDateTime.now());
        admin.setRoles(expectedRoles);

        User resultAdmin = userDao.create(admin);

        SiteInformation siteInformation = new RealSiteInformation();
        siteInformation.setAdmin(resultAdmin);
        siteInformation.setText("HELLO KITTY");
        siteInformation.setType(SiteInformationType.ADVERTISEMENT);
        siteInformation.setHeader("NCADV");

        SiteInformation resultSiteInformation = siteInformationService.create(siteInformation);

        boolean isDeleted = siteInformationService.delete(resultSiteInformation);

        SiteInformation actualSiteInformation = siteInformationService.findById(resultSiteInformation.getId());

        Assert.assertEquals(true, isDeleted);
        Assert.assertEquals(null, actualSiteInformation);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserByIdTest() {
        Set<Role> expectedRoles = new HashSet <>();
        expectedRoles.add(Role.ADMIN);

        User admin = new RealUser();
        admin.setAddress(addressDao.findById(1L));
        admin.setEmail("junit@service.mail");
        admin.setLastName("Vasya");
        admin.setFirstName("Junit");
        admin.setPassword("123");
        admin.setPhoneNumber("0932781395");
        admin.setRegistrationDate(LocalDateTime.now());
        admin.setRoles(expectedRoles);

        User resultAdmin = userDao.create(admin);

        SiteInformation siteInformation = new RealSiteInformation();
        siteInformation.setAdmin(resultAdmin);
        siteInformation.setText("HELLO KITTY");
        siteInformation.setType(SiteInformationType.ADVERTISEMENT);
        siteInformation.setHeader("NCADV");

        SiteInformation resultSiteInformation = siteInformationService.create(siteInformation);

        boolean isDeleted = siteInformationService.delete(resultSiteInformation.getId());

        SiteInformation actualSiteInformation = siteInformationService.findById(resultSiteInformation.getId());

        Assert.assertEquals(true, isDeleted);
        Assert.assertEquals(null, actualSiteInformation);
    }
}
