package ncadvanced2018.groupeone.parent.service;


import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
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
public class OfficeServiceTest {

    @Autowired
    private OfficeService officeService;
    @Autowired
    private AddressDao addressDAO;

    @Test
    @Transactional
    @Rollback
    public void createOfficeTest() {
        String expectedStreetAddress = "Testing";
        String expectedNameOffice = "Test1";

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet(expectedStreetAddress);

        Office office = new RealOffice();
        office.setAddress(address);
        office.setName(expectedNameOffice);
        office.setDescription("Testing");

        Office resultOffice = officeService.create(office);
        Address resultAddress = office.getAddress();

        Assert.assertEquals(expectedNameOffice, resultOffice.getName());
        Assert.assertEquals(expectedStreetAddress, resultAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback
    public void updateOfficeTest() {

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        Office office = new RealOffice();
        office.setAddress(address);
        office.setName("Test1");
        office.setDescription("Testing");

        Office resultOffice = officeService.create(office);
        Address resultAddress = office.getAddress();

        String expectedStreetAddress = "TestingNew";
        String expectedNameOffice = "Test1New";

        resultAddress.setStreet(expectedStreetAddress);
        resultOffice.setName(expectedNameOffice);

        Office actualOffice = officeService.update(resultOffice);
        Address actualAddress = actualOffice.getAddress();

        Assert.assertEquals(expectedNameOffice, actualOffice.getName());
        Assert.assertEquals(expectedStreetAddress, actualAddress.getStreet());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteOfficeTest() {

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        Office office = new RealOffice();
        office.setAddress(address);
        office.setName("Test1");
        office.setDescription("Testing");

        Office resultOffice = officeService.create(office);
        Address resultAddress = office.getAddress();

        boolean isDeleted = officeService.delete(resultOffice);

        Office actualOffice = officeService.findById(resultOffice.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());

        Assert.assertEquals(null, actualOffice);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteOfficeByIdTest() {

        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Testing");

        Office office = new RealOffice();
        office.setAddress(address);
        office.setName("Test1");
        office.setDescription("Testing");

        Office resultOffice = officeService.create(office);
        Address resultAddress = office.getAddress();

        boolean isDeleted = officeService.delete(resultOffice.getId());

        Office actualOffice = officeService.findById(resultOffice.getId());
        Address actualAddress = addressDAO.findById(resultAddress.getId());

        Assert.assertEquals(null, actualOffice);
        Assert.assertEquals(null, actualAddress);
        Assert.assertEquals(true, isDeleted);
    }

    @Test
    @Transactional
    @Rollback
    public void findByNameTest() {

        Office office1 = new RealOffice();
        office1.setAddress(addressDAO.findById(1L));
        office1.setName("Test1");
        office1.setDescription("Testing");

        officeService.create(office1);

        Office office2 = new RealOffice();
        office2.setAddress(addressDAO.findById(1L));
        office2.setName("Test2");
        office2.setDescription("Testing");

        officeService.create(office2);

        List <Office> actualOffices = officeService.findByName("Test");

        long actualSize = actualOffices.size(),
                sizeAfterFiltering = actualOffices.stream().filter(x -> x.getName().contains("Test")).count();

        Assert.assertEquals(actualSize, sizeAfterFiltering);
    }

    @Test
    @Transactional
    @Rollback
    public void findByStreetTest() {

        Address address = new RealAddress();
        address.setStreet("TestingJUnit");
        address.setFloor(1);
        address.setHouse("29D");
        address.setFlat(44);

        Address realAddress = addressDAO.create(address);

        Office office1 = new RealOffice();
        office1.setAddress(realAddress);
        office1.setName("Test1");
        office1.setDescription("Testing");

        officeService.create(office1);

        Office office2 = new RealOffice();
        office2.setAddress(realAddress);
        office2.setName("Test2");
        office2.setDescription("Testing");

        officeService.create(office2);

        List <Office> actualOffices = officeService.findByStreet("Test");

        long actualSize = actualOffices.size(),
                sizeAfterFiltering = actualOffices.stream().filter(x -> x.getAddress().getStreet().
                        contains("Test")).count();
        Assert.assertEquals(actualSize, sizeAfterFiltering);
    }
}
