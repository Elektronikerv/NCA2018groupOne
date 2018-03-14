package ncadvanced2018.groupeone.parent.service.impl;


import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.dto.OrderAction;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.CourierSearchService;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CourierSearchServiceImpl implements CourierSearchService {

    private FulfillmentOrderDao fulfillmentOrderDao;
    private EmployeeService employeeService;
    private MapsService mapsService;

    @Autowired
    public CourierSearchServiceImpl(FulfillmentOrderDao fulfillmentOrderDao,
                                    EmployeeService employeeService, MapsService mapsService) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.employeeService = employeeService;
        this.mapsService = mapsService;
    }

    @Override
    public List<CourierPoint> getCourierWay(Long courierId) {

        List<CourierPoint> courierWay = fulfillmentOrderDao.getCourierWay(courierId);
        courierWay.sort(Comparator.comparing(CourierPoint::getTime));
        return courierWay;
    }

    @Override
    public User searchCourier(Order order) {
        List<User> couriers = employeeService.findAllFreeCouriers();

        if (couriers == null) {
            couriers = employeeService.findAllCouriers();
        }

        Address senderAddress = order.getSenderAddress();

        Optional<User> first = couriers.stream()
                .filter(courier -> getNumbersOfOrders(getCourierWay(courier.getId())) < 5)
                .min(Comparator.comparingLong(courier -> mapsService.getDistanceTime(senderAddress, courier.getCurrentPosition())));
        return first.get();
    }

    private int getNumbersOfOrders(List<CourierPoint> courierPoints) {
        int numberOfOrders = 0;
        for (CourierPoint point : courierPoints) {
            if (point.getOrderAction() == OrderAction.GIVE) {
                numberOfOrders++;
            }
        }
        return numberOfOrders;
    }

}
