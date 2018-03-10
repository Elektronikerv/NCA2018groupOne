package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;

import java.util.List;

public interface FulfillmentOrderDao extends CrudDao <FulfillmentOrder, Long> {

//    List<FulfillmentOrder> findByCourierAndByStatus (Long courierId, Long orderStatusId);

    List<FulfillmentOrder> findByStatusByCourier (Long courierId, Long orderStatusId);

    FulfillmentOrder updateWithInternals(FulfillmentOrder fulfillmentOrder);

    List<CourierPoint> getCourierWay(Long courierId);


    }
