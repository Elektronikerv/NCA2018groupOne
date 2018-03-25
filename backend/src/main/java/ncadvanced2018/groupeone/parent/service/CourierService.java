package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.util.List;

public interface CourierService {

    List<FulfillmentOrder> findFulfillmentOrdersByCourier(Long courierId);

    void orderReceived(CourierPoint courierPoint);

    void cancelReceiving(CourierPoint courierPoint);

    void cancelDelivering(CourierPoint courierPoint);

    void orderDelivered(CourierPoint courierPoint);

    List<CourierPoint> getCourierWay(Long courierId);

    boolean searchCourier(Order order);

    void searchCourierForOrderInAnticipation();

}
