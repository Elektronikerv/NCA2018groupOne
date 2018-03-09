package ncadvanced2018.groupeone.parent.listener;


import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.OrderService;
import ncadvanced2018.groupeone.parent.service.UserService;
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
public class UpdateOrderListenerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    @Rollback
    public void handleTest() {
        Address address = new RealAddress();
        address.setFlat(1234);
        address.setHouse("12");
        address.setFloor(1);
        address.setStreet("Nizhynska");

        User user = new RealUser();
        user.setAddress(address);
        user.setEmail("junit@service.mail");
        user.setLastName("LastName");
        user.setFirstName("Junit");
        user.setPassword("123");
        user.setPhoneNumber("0932781395");
        user.setRegistrationDate(LocalDateTime.now());

        User resultUser = userService.create(user);

        Order originOrder = new RealOrder();
        originOrder.setOrderStatus(OrderStatus.ASSOCIATED);
        originOrder.setUser(resultUser);
        originOrder.setReceiverAddress(resultUser.getAddress());

        Order resultOriginOrder = orderService.create(originOrder);
        resultOriginOrder.setOrderStatus(OrderStatus.CONFIRMED);
        Order resultUpdatedOrder = orderService.update(resultOriginOrder);

        Assert.assertEquals(resultUpdatedOrder.getOrderStatus(), OrderStatus.CONFIRMED);

    }
}
