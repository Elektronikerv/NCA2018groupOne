package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveredOrderEvent extends ApplicationEvent {
    private Order order;

    public DeliveredOrderEvent(Object source, Order order) {
        super(source);
        this.order = order;

    }

}
