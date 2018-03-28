package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapsServiceTest {

    @Autowired
    private MapsService mapsService;

    @Test
    public void getDistanceTest(){
        Address addressOrigin = new RealAddress();
        addressOrigin.setHouse("4");
        addressOrigin.setStreet("Vidradnyi Ave");

        Address addressDestination = new RealAddress();
        addressDestination.setHouse("3A");
        addressDestination.setStreet("Kombaineriv St");

        long distance = mapsService.getDistance(addressOrigin, addressDestination);
        Assert.assertNotEquals(distance, 0L);

    }

    @Test
    public void getDistanceTimeTest(){

        Address addressOrigin = new RealAddress();
        addressOrigin.setHouse("4");
        addressOrigin.setStreet("Vidradnyi Ave");

        Address addressDestination = new RealAddress();
        addressDestination.setHouse("3A");
        addressDestination.setStreet("Kombaineriv St");

        long distanceTime = mapsService.getDistanceTime(addressOrigin, addressDestination);
        Assert.assertNotEquals(distanceTime, 0L);

    }

}
