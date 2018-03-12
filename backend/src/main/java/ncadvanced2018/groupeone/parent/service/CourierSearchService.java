package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.util.List;

public interface CourierSearchService {

    User searchCourier(Order order);

    List<CourierPoint> getCourierWay(Long courierId);

}
