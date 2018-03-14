package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateOrderEvent extends ApplicationEvent {

    private Order originalOrder;
    private Order updatedOrder;
    private boolean changedToConfirmedStatus;
    private boolean changedToOpenStatus;
    private boolean changedToDeliveredStatus;
    private boolean changedToDeliveringStatus;

    public UpdateOrderEvent(Object source, Order originalOrder, Order updatedOrder) {
        super(source);
        this.originalOrder = originalOrder;
        this.updatedOrder = updatedOrder;
        changedToConfirmedStatus = originalOrder != null && originalOrder.getOrderStatus() != OrderStatus.CONFIRMED &&
                updatedOrder.getOrderStatus() == OrderStatus.CONFIRMED;
        changedToDeliveringStatus = originalOrder != null && originalOrder.getOrderStatus() != OrderStatus.DELIVERING &&
                updatedOrder.getOrderStatus() == OrderStatus.DELIVERING;
        changedToDeliveredStatus = originalOrder != null && originalOrder.getOrderStatus() != OrderStatus.DELIVERED &&
                updatedOrder.getOrderStatus() == OrderStatus.DELIVERED;
    }

}
