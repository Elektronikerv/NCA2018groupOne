package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.AdvertType;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAdvert;
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
public class AdvertServiceTest {

    @Autowired
    private AdvertService advertService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void createsAdvertTest() {
        Set <Role> expectedRoles = new HashSet <>();
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

        Advert advert = new RealAdvert();
        advert.setAdmin(resultAdmin);
        advert.setText(expectedText);
        advert.setType(AdvertType.ADVERTISEMENT);
        advert.setHeader("NCADV");
        advert.setDateOfPublishing(LocalDateTime.now());

        Advert advertActual = advertService.create(advert);

        Assert.assertEquals(expectedText, advertActual.getText());

    }

    @Test
    @Transactional
    @Rollback
    public void updateSiteInformationTest() {

        Set <Role> expectedRoles = new HashSet <>();
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

        Advert advert = new RealAdvert();
        advert.setAdmin(createdAdmin);
        advert.setText(actualText);
        advert.setType(AdvertType.ADVERTISEMENT);
        advert.setHeader("NCADV");
        advert.setDateOfPublishing(LocalDateTime.now());

        Advert advertInformation = advertService.create(advert);

        String expectedText = "TestingNew";
        advertInformation.setText(expectedText);
        advertService.update(advertInformation);

        Advert actualSiteInformation = advertService.findById(advertInformation.getId());
        Assert.assertEquals(expectedText, actualSiteInformation.getText());

    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserTest() {
        Set <Role> expectedRoles = new HashSet <>();
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

        Advert advert = new RealAdvert();
        advert.setAdmin(resultAdmin);
        advert.setText("HELLO KITTY");
        advert.setType(AdvertType.ADVERTISEMENT);
        advert.setHeader("NCADV");
        advert.setDateOfPublishing(LocalDateTime.now());

        Advert resultAdvert = advertService.create(advert);

        boolean isDeleted = advertService.delete(resultAdvert);

        Advert actualAdvert = advertService.findById(resultAdvert.getId());

        Assert.assertEquals(true, isDeleted);
        Assert.assertEquals(null, actualAdvert);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserByIdTest() {
        Set <Role> expectedRoles = new HashSet <>();
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

        Advert advert = new RealAdvert();
        advert.setAdmin(resultAdmin);
        advert.setText("HELLO KITTY");
        advert.setType(AdvertType.ADVERTISEMENT);
        advert.setHeader("NCADV");
        advert.setDateOfPublishing(LocalDateTime.now());

        Advert resultSiteInformation = advertService.create(advert);

        boolean isDeleted = advertService.delete(resultSiteInformation.getId());

        Advert actualAdvert = advertService.findById(resultSiteInformation.getId());

        Assert.assertEquals(true, isDeleted);
        Assert.assertEquals(null, actualAdvert);
    }
}
