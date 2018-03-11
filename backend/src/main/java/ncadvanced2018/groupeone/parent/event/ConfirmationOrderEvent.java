package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;


@Data
public class ConfirmationOrderEvent extends ApplicationEvent {


    private Order originalOrder;
    private Order updatedOrder;
    private boolean changedToConfirmedStatus;

    public ConfirmationOrderEvent(Object source, Order originalOrder, Order updatedOrder) {
        super(source);
        this.originalOrder = originalOrder;
        this.updatedOrder = updatedOrder;
        changedToConfirmedStatus = originalOrder.getOrderStatus() != OrderStatus.CONFIRMED &&
                updatedOrder.getOrderStatus() == OrderStatus.CONFIRMED;
    }

}

