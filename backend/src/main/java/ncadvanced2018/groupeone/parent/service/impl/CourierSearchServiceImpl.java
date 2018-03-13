package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.dto.OrderAction;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.CourierSearchService;
import ncadvanced2018.groupeone.parent.service.CourierService;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CourierSearchServiceImpl implements CourierSearchService {

    private FulfillmentOrderDao fulfillmentOrderDao;
    private EmployeeService employeeService;
    private MapsService mapsService;
    private CourierSearchService courierSearchService;

    @Autowired
    public CourierSearchServiceImpl(FulfillmentOrderDao fulfillmentOrderDao,
                                    EmployeeService employeeService, MapsService mapsService,
                                    CourierSearchService courierSearchService) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.employeeService = employeeService;
        this.mapsService = mapsService;
        this.courierSearchService = courierSearchService;
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

        Address officeAddress = order.getOffice().getAddress();

        Optional<User> first = couriers.stream()
                .filter(courier -> courierSearchService.getCourierWay(courier.getId()).size() < 10)
                .sorted(Comparator.comparingLong(courier -> mapsService.getDistance(officeAddress, courier.getCurrentPosition())))
                .findFirst();
        return first.get();
    }

}
