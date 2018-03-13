package ncadvanced2018.groupeone.parent.service;

import java.util.PriorityQueue;
import java.util.SortedSet;

public interface CcagentWorkloadService<T, K> {

    void executeWorkloadDistributionAfterConfirmation();

    void executeWorkloadDistributionAfterOpening();

}
