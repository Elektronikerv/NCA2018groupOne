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
    private CourierService courierService;

    @Autowired
    public CourierSearchServiceImpl(FulfillmentOrderDao fulfillmentOrderDao,
                                    EmployeeService employeeService, MapsService mapsService,
                                    CourierService courierService) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.employeeService = employeeService;
        this.mapsService = mapsService;
        this.courierService = courierService;
    }

    @Override
    public List<CourierPoint> getCourierWay(Long courierId) {
        List<CourierPoint> courierWay = fulfillmentOrderDao.getCourierWay(courierId);
        courierWay.sort(Comparator.comparing(CourierPoint::getTime));
        return courierWay;
    }

    @Override
    public void searchCourier(Order order) {
        List<User> couriers = employeeService.findAllFreeCouriers();

        if (couriers == null) {
            couriers = employeeService.findAllCouriers();
        }

        Address officeAddress = order.getOffice().getAddress();
        Address receiverAddress = order.getReceiverAddress();

        //the last receiver address
//        final Comparator<User> comp =;

        Optional<User> first = couriers.stream()
                .filter(courier -> courier.getDeque().size() < 5)
                .sorted(Comparator.comparingLong(courier -> mapsService.getDistance(officeAddress, courier.getCurrentPosition())))
                .findFirst();
        User choosenCourier = first.get();

//        if(!choosenCourier.getQueue().isEmpty()){
////
////        }

        CourierPoint courierPointTake = new CourierPoint();
        courierPointTake.setOrder(order);
        courierPointTake.setOrderAction(OrderAction.TAKE);
//        courierPointTake.setStartTime();//время нужное для достижения даной точки от места нахождения

        CourierPoint courierPointGive = new CourierPoint();
        courierPointGive.setOrder(order);
        courierPointGive.setOrderAction(OrderAction.GIVE);
//        courierPointGive.setEndTime();

        choosenCourier.getDeque().addFirst(courierPointTake);
        choosenCourier.getDeque().addFirst(courierPointGive);
//        RealFulfillmentOrder fulfillmentOrder = (RealFulfillmentOrder) order.getFulfillmentOrder();
//        fulfillmentOrder.setCourier(choosenCourier);
//        choosenCourier.getQueue().add(fulfillmentOrder);


    }

}
