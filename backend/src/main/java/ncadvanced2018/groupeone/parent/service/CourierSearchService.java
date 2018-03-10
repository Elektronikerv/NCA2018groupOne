package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.util.List;

public interface CourierSearchService {

    void searchCourier(Order order);

    List<CourierPoint> getCourierWay(Long courierId);

}
