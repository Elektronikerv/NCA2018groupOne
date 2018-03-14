package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.event.UpdateOrderEvent;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.CourierSearchService;
import ncadvanced2018.groupeone.parent.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateOrderListener {


    private CourierSearchService courierSearchService;
    private CourierService courierService;
    private FulfillmentOrderDao fulfillmentOrderDao;

    @Autowired
    public UpdateOrderListener(CourierSearchService courierSearchService, CourierService courierService,
                               FulfillmentOrderDao fulfillmentOrderDao) {
        this.courierSearchService = courierSearchService;
        this.courierService = courierService;
        this.fulfillmentOrderDao = fulfillmentOrderDao;
    }


    @EventListener(condition = "#updateEvent.changedToConfirmedStatus")
    public void handleOrderCreatedEvent(UpdateOrderEvent updateEvent) {
        Order updatedOrder = updateEvent.getUpdatedOrder();
        User courier = courierSearchService.searchCourier(updatedOrder);
        if(courierSearchService.getCourierWay(courier.getId()).isEmpty()){
            courierService.putOrderToFreeCourier(courier, updatedOrder);
        }else{
            courierService.putOrderToCourier(courier, updatedOrder);
        }
        FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findFulfillmentByOrder(updatedOrder);
        fulfillmentOrder.setCourier(courier);
        fulfillmentOrderDao.update(fulfillmentOrder);
    }


}
