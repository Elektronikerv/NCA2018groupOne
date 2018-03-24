package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;

@Data
public class OrderStatusEvent extends ApplicationEvent {

    private Order order;
    private boolean isOpenStatus;
    private boolean isConfirmedStatus;

    public OrderStatusEvent(Object source, Order order) {
        super(source);
        this.order = order;
        isConfirmedStatus = order.getOrderStatus() == OrderStatus.CONFIRMED;
        isOpenStatus = order.getOrderStatus() == OrderStatus.OPEN ;
    }

}
