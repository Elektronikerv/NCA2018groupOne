package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.service.CourierSearchService;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import ncadvanced2018.groupeone.parent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CourierSearchServiceImpl implements CourierSearchService {

    private FulfillmentOrderDao fulfillmentOrderDao;

    @Autowired
    public CourierSearchServiceImpl(FulfillmentOrderDao fulfillmentOrderDao) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
    }

    @Override
    public List<CourierPoint> getCourierWay(Long courierId) {
        List<CourierPoint> courierWay = fulfillmentOrderDao.getCourierWay(courierId);
        courierWay.sort(Comparator.comparing(CourierPoint::getTime));
        return courierWay;
    }

}
