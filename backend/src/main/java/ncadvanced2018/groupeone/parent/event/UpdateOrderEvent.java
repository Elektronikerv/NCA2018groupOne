package ncadvanced2018.groupeone.parent.event;

import ncadvanced2018.groupeone.parent.model.entity.Order;
import org.springframework.context.ApplicationEvent;

public class UpdateOrderStatusEvent extends ApplicationEvent {

    public UpdateOrderStatusEvent(Object source, Order order) {
        super(source);
    }
}
