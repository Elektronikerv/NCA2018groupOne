package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Office;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOffice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OfficeDaoTest {

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void insertOfficeTest() {
        Office expected = new RealOffice();
        expected.setName("Junit");
        expected.setDescription("Junit");
        expected.setAddress(addressDao.findById(1L));

        officeDao.create(expected);
        Office actual = officeDao.findById(expected.getId());

        Assert.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @Transactional
    @Rollback
    public void insertOfficeWithNullsTest() {
        Office expected = new RealOffice();
        expected.setName("Junit");
        expected.setAddress(addressDao.findById(1L));

        officeDao.create(expected);
        Office actual = officeDao.findById(expected.getId());

        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void updateOfficeTest() {
        Office expected = officeDao.findById(5L);
        expected.setName("Junit");
        expected.setDescription("Junit");
        expected.setAddress(addressDao.findById(1L));

        officeDao.update(expected);
        Office actual = officeDao.findById(5L);

        Assert.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteOfficeTest() {
        Office expected = new RealOffice();
        expected.setName("Junit");
        expected.setDescription("Junit");
        expected.setAddress(addressDao.findById(1L));

        Office office = officeDao.create(expected);
        officeDao.delete(office);

        Assert.assertNull(officeDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findOfficeById() {
        Long expectedId = 4L;
        Office office = officeDao.findById(expectedId);

        log.info("Fetched office by id: {}", office.getId());
        Assert.assertEquals(expectedId, office.getId());
    }

    @Test
    @Transactional
    public void shouldFindAll() {
        int countRowInDB = 8;
        List<Office> all = officeDao.findAll();
        Assert.assertEquals(all.size(), countRowInDB);
    }


    @Rollback
    public void findOfficeByName() {

        Office expectedOne = new RealOffice();
        expectedOne.setName("June");
        expectedOne.setDescription("Junit");
        expectedOne.setAddress(addressDao.findById(1L));

        Office expectedTwo = new RealOffice();
        expectedTwo.setName("Junit");
        expectedTwo.setDescription("June");
        expectedTwo.setAddress(addressDao.findById(2L));

        officeDao.create(expectedOne);
        officeDao.create(expectedTwo);

        long actualSize = officeDao.findByName("Jun").size();
        long expectedSize = 2;
        Assert.assertEquals(actualSize, expectedSize);
    }

}