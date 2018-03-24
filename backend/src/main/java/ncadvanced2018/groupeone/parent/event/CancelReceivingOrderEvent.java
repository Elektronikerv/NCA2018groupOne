package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class CancelReceivingOrderEvent extends ApplicationEvent {

    private Order order;


    public CancelReceivingOrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

}
