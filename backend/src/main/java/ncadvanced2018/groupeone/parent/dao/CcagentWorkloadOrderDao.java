package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.dto.CcagentWorkload;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;

import java.util.List;
import java.util.SortedSet;
import java.util.Queue;

public interface CcagentWorkloadOrderDao<T, K> {


    List<K> findWorkingCcagents() ;

    List<T> findFulfillmentsForExecuting();

    List<T> updateFulfillmentOrders(Queue<T> fulfillmentOrders);


}
