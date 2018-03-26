package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.*;
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
    public void handleOrderCreatedEvent(ConfirmOrderEvent updateEvent) {
        Order updatedOrder = updateEvent.getUpdatedOrder();
        courierService.searchCourier(updatedOrder);
    }

    @EventListener
    public void handleOrderDeliveringEvent(DeliveringOrderEvent event) {
        courierService.searchCourierForOrderInAnticipation();
    }

    @EventListener
    public void handleOrderDeliveredEvent(DeliveredOrderEvent event) {
        courierService.searchCourierForOrderInAnticipation();
    }

    @EventListener
    public void handleCancelReceivingOrderEvent(CancelReceivingOrderEvent event) {
        courierService.searchCourierForOrderInAnticipation();
    }

    @EventListener
    public void handleCancelDeliveringOrderEvent(CancelDeliveringOrderEvent event) {
        courierService.searchCourierForOrderInAnticipation();
    }


}
