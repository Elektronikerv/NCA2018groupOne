package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.util.List;

public interface CourierService {

    List<FulfillmentOrder> findFulfillmentOrdersByCourier(Long courierId);

    CourierPoint orderReceived(CourierPoint courierPoint);

    CourierPoint cancelReceiving(CourierPoint courierPoint);

    CourierPoint cancelDelivering(CourierPoint courierPoint);

    CourierPoint orderDelivered(CourierPoint courierPoint);

    List<CourierPoint> getCourierWay(Long courierId);

    boolean searchCourier(Order order);

}
