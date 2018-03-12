package ncadvanced2018.groupeone.parent.service;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
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
public class TransitServiceTest {

    @Autowired
    private TransitService transitService;

    @Test
    @Transactional
    @Rollback
    public void isTransitPossible(){
        String expectedStreet = "Vidradnyi Ave";
        String expectedStreetTwo = "Kharkivs'ke Hwy";

        Address senderAddress = new RealAddress();
        senderAddress.setHouse("89");
        senderAddress.setStreet(expectedStreet);

        Address receiverAddress = new RealAddress();
        receiverAddress.setHouse("201");//201
        receiverAddress.setStreet(expectedStreetTwo);
        User user = new RealUser();
        user.setId(9L);
        OrderStatus status = OrderStatus.valueOf(1L);

        Order order = new RealOrder();
        order.setSenderAddress(senderAddress);
        order.setReceiverAddress(receiverAddress);
        order.setOrderStatus(status);
        order.setUser(user);

        String expectedSecondStreet = "Vidradnyi Ave";
        String expectedSecondStreetTwo = "Kharkivs'ke Hwy";

        Address senderSecondAddress = new RealAddress();
        senderSecondAddress.setHouse("4");
        senderSecondAddress.setStreet(expectedSecondStreet);

        Address receiverSecondAddress = new RealAddress();
        receiverSecondAddress.setHouse("1");//1
        receiverSecondAddress.setStreet(expectedSecondStreetTwo);

        User userSecond = new RealUser();
        userSecond.setId(10L);
        OrderStatus statusSecond = OrderStatus.valueOf(1L);

        Order orderSecond = new RealOrder();
        orderSecond.setSenderAddress(senderSecondAddress);
        orderSecond.setReceiverAddress(receiverSecondAddress);
        orderSecond.setOrderStatus(statusSecond);
        orderSecond.setUser(userSecond);

        order.setReceiveravAilabilityTimeFrom(LocalDateTime.now().plusMinutes(30));
        order.setReceiveravAilabilityTimeTo(LocalDateTime.now().plusMinutes(60));
        orderSecond.setReceiveravAilabilityTimeFrom(LocalDateTime.now().plusMinutes(30));
        orderSecond.setReceiveravAilabilityTimeTo(LocalDateTime.now().plusMinutes(60));

        boolean transitPossible = transitService.isTransitPossible(order, orderSecond);

        System.out.println(transitPossible);

    }

}
