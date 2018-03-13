package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.CcagentWorkloadOrderDao;
import ncadvanced2018.groupeone.parent.dto.CcagentWorkload;
import ncadvanced2018.groupeone.parent.dto.Fulfillment;
import ncadvanced2018.groupeone.parent.service.CcagentWorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
@PropertySource("classpath:workload.properties")
public class CcagentWorkloadServiceImpl implements CcagentWorkloadService<Fulfillment, CcagentWorkload> {

    private CcagentWorkloadOrderDao<Fulfillment, CcagentWorkload> ccagentWorkloadOrderDao;
    private PriorityQueue<Fulfillment> fulfillmentsForExecuting;
    private PriorityQueue<CcagentWorkload> workingCcagents;
    private Comparator<CcagentWorkload> ccagentWorkloadComparator;
    private Comparator<Fulfillment> fulfillmentOrderComparator;

    @Value("${workload.maxOrdersPerCcagent}")
    private Integer maxOrdersPerCcagent;
    @Value("${workload.minutesPerOrder}")
    private Integer minutesPerOrder;

    private Integer limitOfOrdersToProcess;

    @Autowired
    public CcagentWorkloadServiceImpl(CcagentWorkloadOrderDao<Fulfillment, CcagentWorkload> ccagentWorkloadOrderDao,
                                      @Qualifier("ordersToTake") Comparator<CcagentWorkload> ccagentWorkloadComparator,
                                      @Qualifier("basic") Comparator<Fulfillment> fulfillmentOrderComparator) {
        this.ccagentWorkloadOrderDao = ccagentWorkloadOrderDao;
        this.ccagentWorkloadComparator = ccagentWorkloadComparator;
        this.fulfillmentOrderComparator = fulfillmentOrderComparator;
    }

    @Override
    public void executeWorkloadDistributionAfterConfirmation() {
        prepareDataForDistribution();
        executeWorkloadDistribution();
   }

    @Override
    public void executeWorkloadDistributionAfterOpening() {
        prepareDataForDistribution();
        if (limitOfOrdersToProcess < fulfillmentsForExecuting.size()) {
            executeWorkloadDistribution();
        }
 }


    private void prepareDataForDistribution() {
        fulfillmentsForExecuting = new PriorityQueue<>(fulfillmentOrderComparator);
        fulfillmentsForExecuting.addAll(ccagentWorkloadOrderDao.findFulfillmentsForExecuting());
        workingCcagents = new PriorityQueue<>(ccagentWorkloadComparator);
        workingCcagents.addAll(ccagentWorkloadOrderDao.findWorkingCcagents());

        workingCcagents
                .forEach(this::calculateQuantityOfOrdersForCourier);

        limitOfOrdersToProcess = workingCcagents.stream()
                .mapToInt(CcagentWorkload::getOrdersToTake)
                .sum();
    }

    private void executeWorkloadDistribution() {
        fulfillmentsForExecuting
                .forEach(fulfillment -> fulfillment.setCcagentId(null));

        fulfillmentsForExecuting.stream()
                .limit(limitOfOrdersToProcess)
                .forEach(fulfillment -> assignFulfilmentToCcagent(fulfillment, workingCcagents.poll()));

        ccagentWorkloadOrderDao.updateFulfillmentOrders(fulfillmentsForExecuting);
    }

    private void calculateQuantityOfOrdersForCourier(CcagentWorkload ccagentWorkload) {

        Integer ordersBeforeWorkdayEnd =  (int) LocalDateTime.now().until(ccagentWorkload.getWorkdayEnd(), ChronoUnit.MINUTES) / minutesPerOrder;
        Integer ordersToTake = maxOrdersPerCcagent - ccagentWorkload.getProcessingOrders();
        ccagentWorkload.setOrdersBeforeEndOfWorkingDay(ordersBeforeWorkdayEnd);
        ccagentWorkload.setOrdersToTake(Integer.min(ordersToTake, ordersBeforeWorkdayEnd));

    }


    private void assignFulfilmentToCcagent(Fulfillment fulfillment, CcagentWorkload ccagentWorkload) {
        fulfillment.setCcagentId(ccagentWorkload.getId());
        countdownOrdersToTake(ccagentWorkload);
        workingCcagents.offer(ccagentWorkload);
    }

    private void countdownOrdersToTake(CcagentWorkload ccagentWorkload) {
        ccagentWorkload.setOrdersToTake(ccagentWorkload.getOrdersToTake() - 1);
    }

}
