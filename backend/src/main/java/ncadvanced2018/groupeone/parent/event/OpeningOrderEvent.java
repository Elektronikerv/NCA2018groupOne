package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import org.springframework.context.ApplicationEvent;

@Data
public class OpeningOrderEvent  extends ApplicationEvent {

    private Order order;

    public OpeningOrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

}
