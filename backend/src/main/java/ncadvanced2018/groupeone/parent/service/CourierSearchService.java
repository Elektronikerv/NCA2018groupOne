package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;

import java.util.List;

public interface CourierSearchService {

    List<CourierPoint> getCourierWay(Long courierId);

}
