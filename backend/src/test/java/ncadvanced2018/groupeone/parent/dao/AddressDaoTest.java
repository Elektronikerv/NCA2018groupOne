package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
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
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void insertAddressTest() {
        Address expected = new RealAddress();
        expected.setStreet("Junit");
        expected.setHouse("Junit");
        expected.setFloor(1);
        expected.setFlat(1);

        addressDao.create(expected);
        Address actual = addressDao.findById(expected.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void updateAddressTest() {
        Address expected = addressDao.findById(10L);
        expected.setStreet("Junit");
        expected.setHouse("Junit");
        expected.setFloor(1);
        expected.setFlat(1);

        addressDao.update(expected);
        Address actual = addressDao.findById(10L);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteAddressTest() {
        Address expected = new RealAddress();
        expected.setStreet("Junit");
        expected.setHouse("Junit");
        expected.setFloor(1);
        expected.setFlat(1);

        Address address = addressDao.create(expected);
        addressDao.delete(address);

        Assert.assertNull(addressDao.findById(expected.getId()));
    }


    @Test
    @Transactional
    @Rollback
    public void findAddressByIdTest() {
        Long expectedId = 11L;
        Address address = addressDao.findById(expectedId);

        log.info("Fetched address by id: {}", address.getId());
        Assert.assertEquals(expectedId, address.getId());
    }
}