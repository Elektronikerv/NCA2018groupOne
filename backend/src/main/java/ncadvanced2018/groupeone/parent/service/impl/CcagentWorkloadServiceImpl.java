package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.CcagentWorkloadOrderDao;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.CcagentWorkload;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.CcagentWorkloadService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
public class CcagentWorkloadServiceImpl implements CcagentWorkloadService<FulfillmentOrder, CcagentWorkload> {

    private CcagentWorkloadOrderDao ccagentWorkloadOrderDao;

    private Integer maxPerCcagent = 5;      // Properties
    private Integer minutesPerOrder = 10;   // Properties

    @Override
    public void executeWorkloadDistribution(PriorityQueue<FulfillmentOrder> fulfillmentOrders, SortedSet<CcagentWorkload> ccagents) {


        ccagents
                .forEach(this::calculateQuantityOfOrdersForCourier);

        Stream.generate(fulfillmentOrders::poll)
                .limit(
                        ccagents.stream()
                                .mapToInt(CcagentWorkload::getOrdersToTake)
                                .sum()
                )
                .forEach(fulfillmentOrder -> assignFulfilmentToCcagent(fulfillmentOrder, ccagents.first()));

    }


    private void calculateQuantityOfOrdersForCourier(CcagentWorkload ccagentWorkload) {
        Integer ordersBeforeWorkdayEnd = (int) ccagentWorkload.getWorkdayEnd().until(LocalDateTime.now(), ChronoUnit.MINUTES) / minutesPerOrder;
        Integer ordersToTake = maxPerCcagent - ccagentWorkload.getProcessingOrders();
        ccagentWorkload.setOrdersToTake(Integer.min(ordersToTake, ordersBeforeWorkdayEnd));
    }

    private User getNextCcagent(CcagentWorkload ccagentWorkload) {
        User user = new RealUser();
        user.setId(countdownOrdersToTake(ccagentWorkload).getId());
        return user;
    }

    private void assignFulfilmentToCcagent(FulfillmentOrder fulfillmentOrder, CcagentWorkload ccagentWorkload) {
        fulfillmentOrder.setCcagent(getNextCcagent(ccagentWorkload));
    }

    private CcagentWorkload countdownOrdersToTake(CcagentWorkload ccagentWorkload) {
        ccagentWorkload.setOrdersToTake(ccagentWorkload.getOrdersToTake() - 1);
        return ccagentWorkload;
    }


}
