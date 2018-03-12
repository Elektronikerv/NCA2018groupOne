package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.Order;

public interface TransitService {

    boolean isTransitPossible(Order order, Order distinctOrder);

}
