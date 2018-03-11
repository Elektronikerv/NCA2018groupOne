package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;

import java.util.List;

public interface FulfillmentOrderDao extends CrudDao<FulfillmentOrder, Long> {

//    List<FulfillmentOrder> findByCourierAndByStatus (Long courierId, Long orderStatusId);

    List<FulfillmentOrder> findByCourier(Long courierId);

    List<FulfillmentOrder> findFulfillmentForCcagent(Long ccagentId);

    FulfillmentOrder updateWithInternals(FulfillmentOrder fulfillmentOrder);

}
