package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;

import java.util.List;

public interface FulfillmentOrderService {

    FulfillmentOrder create(FulfillmentOrder fulfillmentOrder);

    FulfillmentOrder findById(Long id);

    FulfillmentOrder update(FulfillmentOrder fulfillmentOrder);

    boolean delete(FulfillmentOrder fulfillmentOrder);

    List<FulfillmentOrder> findFulfillmentOrderByStatusByCourier(Long statusId, Long courierId);

}
