package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.UpdateOrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateOrderListener {

    @EventListener(condition = "#updateEvent.changedToConfirmedStatus")
    public void handleOrderCreatedEvent(UpdateOrderEvent updateEvent) {

    }
}
