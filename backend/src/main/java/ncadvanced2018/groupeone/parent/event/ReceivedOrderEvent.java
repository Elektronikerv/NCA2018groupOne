package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import org.springframework.context.ApplicationEvent;

@Data
public class ReceivedOrderEvent extends ApplicationEvent {

    private FulfillmentOrder fulfillmentOrder;

    public ReceivedOrderEvent(Object source, FulfillmentOrder fulfillmentOrder) {
        super(source);
        this.fulfillmentOrder = fulfillmentOrder;
    }
}
