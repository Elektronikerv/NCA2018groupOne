package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;

import java.util.List;

public interface FulfillmentService {

    List<FulfillmentOrder> findFulfillmentForCcagent(Long ccagentId);

    FulfillmentOrder findById(Long id);

}
