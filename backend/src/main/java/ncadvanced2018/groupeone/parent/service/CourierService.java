package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.dto.OrderAction;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.util.List;

public interface CourierService {

    List<FulfillmentOrder> findFulfillmentOrdersByCourier(Long courierId);

    FulfillmentOrder orderReceived(FulfillmentOrder fulfillment);

    FulfillmentOrder isntReceived(FulfillmentOrder fulfillment);

    FulfillmentOrder cancelExecution(FulfillmentOrder fulfillment);

    FulfillmentOrder cancelDelivering(FulfillmentOrder fulfillment);

    FulfillmentOrder orderDelivered(FulfillmentOrder fulfillment);

    FulfillmentOrder isntDelivered(FulfillmentOrder fulfillment);

    void putOrderToFreeCourier(User courier, Order order);

    void putOrderToCourier(User courier, Order order);

}
