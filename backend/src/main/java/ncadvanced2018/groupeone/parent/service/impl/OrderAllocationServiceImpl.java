package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.service.CourierSearchService;
import ncadvanced2018.groupeone.parent.service.OrderAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAllocationServiceImpl implements OrderAllocationService {

    private CourierSearchService courierSearchService;

    @Autowired
    public OrderAllocationServiceImpl(CourierSearchService courierSearchService) {
        this.courierSearchService = courierSearchService;
    }


}
