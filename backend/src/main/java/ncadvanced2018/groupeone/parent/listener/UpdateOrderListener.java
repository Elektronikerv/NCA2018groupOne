package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.UpdateOrderEvent;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateOrderListener {


    private CourierService courierService;

    @Autowired
    public UpdateOrderListener(CourierService courierService) {
        this.courierService = courierService;
    }


    @EventListener(condition = "#updateEvent.changedToConfirmedStatus")
    public void handleOrderCreatedEvent(UpdateOrderEvent updateEvent) {
        Order updatedOrder = updateEvent.getUpdatedOrder();
        courierService.searchCourier(updatedOrder);
    }

    @EventListener(condition = "#updateEvent.changedToDeliveredStatus || #updateEvent.changedToDeliveringStatus")
    public void handleOrderCourierEvents(UpdateOrderEvent updateEvent) {
        Order updatedOrder = updateEvent.getUpdatedOrder();
    }


}
