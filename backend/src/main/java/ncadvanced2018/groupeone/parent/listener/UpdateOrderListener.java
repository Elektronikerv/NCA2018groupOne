package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.UpdateOrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateOrderListenerTest {

    //@EventListener(condition = "#updateEvent.changedToConfirmedStatus")
    @EventListener
    public void handleOrderCreatedEvent(UpdateOrderEvent updateEvent) {
        log.info("Origin order status: ", updateEvent.getOriginalOrder().getOrderStatus());
        log.info("Changed order status: ", updateEvent.getUpdatedOrder().getOrderStatus());
        System.out.println(updateEvent.getUpdatedOrder());
    }
}
