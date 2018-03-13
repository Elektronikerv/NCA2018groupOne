package ncadvanced2018.groupeone.parent.comparator.impl;

import ncadvanced2018.groupeone.parent.comparator.CcagentWorkloadComparator;
import ncadvanced2018.groupeone.parent.dto.CcagentWorkload;
import org.springframework.stereotype.Component;


@Component("ordersToTake")
public class CcagentWorkloadComparatorImpl implements CcagentWorkloadComparator {

    @Override
    public int compare(CcagentWorkload o1, CcagentWorkload o2) {
        int ordersToTakeCompare = o2.getOrdersToTake().compareTo( o1.getOrdersToTake());
        if (ordersToTakeCompare == 0){
            return o1.getId().compareTo(o2.getId());
        }
        return ordersToTakeCompare;

    }

}
