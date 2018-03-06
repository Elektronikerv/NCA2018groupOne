package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import java.util.List;

public interface CourierService {

    List<FulfillmentOrder> findFulfillmentOrderByStatusByCourier(Long statusId, Long courierId);

    FulfillmentOrder cancelAssignment(FulfillmentOrder fulfillment);

    FulfillmentOrder acceptOrderForDelivering(FulfillmentOrder fulfillment);

    FulfillmentOrder cancelDelivery(FulfillmentOrder fulfillment);

    FulfillmentOrder confirmExecution(FulfillmentOrder fulfillment);

    FulfillmentOrder isNotDelivered(FulfillmentOrder fulfillment);

}
