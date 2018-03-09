package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.UserDao;
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

@Service
public class CourierSearchServiceImpl implements CourierSearchService {

    private EmployeeService employeeService;
    private MapsService mapsService;

    @Autowired
    public CourierSearchServiceImpl(EmployeeService employeeService, MapsService mapsService) {
        this.employeeService = employeeService;
        this.mapsService = mapsService;
    }

    @Override
    public User findCourierByOrder(Order order) {
        List<User> couriers = employeeService.findAllFreeCouriers();
        if (couriers == null)
            couriers = employeeService.findAllCouriers();

        Address officeAddress = order.getOffice().getAddress();
        Address receiverAddress = order.getReceiverAddress();

        final Comparator<User> comp = Comparator.comparingLong(courier -> mapsService.getDistance(officeAddress, courier.getCurrentPosition())
                + mapsService.getDistance(receiverAddress, courier.getCurrentPosition()));

        return couriers.stream().min(comp).get();
    }

}
