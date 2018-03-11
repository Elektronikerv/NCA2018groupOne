package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;

@Data
public class OpenOrderEvent extends ApplicationEvent {

    private Order openOrder;
    private boolean changedToOpenStatus;

    public OpenOrderEvent(Object source, Order openOrder) {
        super(source);
        this.openOrder = openOrder;
        changedToOpenStatus = openOrder.getOrderStatus() == OrderStatus.OPEN ;
    }

}
