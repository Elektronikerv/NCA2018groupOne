package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.ConfirmationOrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfirmationOrderListener {

    @EventListener(condition = "#confirmationOrderEvent.changedToConfirmedStatus")
    public void handleOrderCreatedEvent(ConfirmationOrderEvent confirmationOrderEvent) {

    }

}
