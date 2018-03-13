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
        System.out.println("executeWorkloadDistributionAfterConfirmation");
    }

    @Override
    public void executeWorkloadDistributionAfterOpening() {
        prepareDataForDistribution();
        executeWorkloadDistribution();
//        if (limitOfOrdersToProcess < fulfillmentsForExecuting.size()) {
//            executeWorkloadDistribution();
//        }
        System.out.println("executeWorkloadDistributionAfterOpening" + minutesPerOrder + "" + maxOrdersPerCcagent);
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

//        Stream.generate(fulfillmentsForExecuting::poll) //poll
//                .limit(limitOfOrdersToProcess)
//                .forEach(fulfillment -> assignFulfilmentToCcagent(fulfillment, workingCcagents.peek()));

        System.out.println(fulfillmentsForExecuting);
        System.out.println(workingCcagents.size());
//        System.out.println(fulfillmentsForExecuting);
//        System.out.println("limitOfOrdersToProcess " + limitOfOrdersToProcess);
        System.out.println(workingCcagents);

        ccagentWorkloadOrderDao.updateFulfillmentOrders(fulfillmentsForExecuting);



    }

    private void calculateQuantityOfOrdersForCourier(CcagentWorkload ccagentWorkload) {

        Integer ordersBeforeWorkdayEnd = (int) LocalDateTime.now().until(ccagentWorkload.getWorkdayEnd(), ChronoUnit.MINUTES) / minutesPerOrder;
        System.out.println(ordersBeforeWorkdayEnd);
        Integer ordersToTake = maxOrdersPerCcagent - ccagentWorkload.getProcessingOrders();
        System.out.println(maxOrdersPerCcagent);
        ccagentWorkload.setOrdersToTake(Integer.min(ordersToTake, ordersBeforeWorkdayEnd));
        System.out.println(Integer.min(ordersToTake, ordersBeforeWorkdayEnd));
    }


    private void assignFulfilmentToCcagent(Fulfillment fulfillment, CcagentWorkload ccagentWorkload) {
        fulfillment.setCcagentId(ccagentWorkload.getId());
        countdownOrdersToTake(ccagentWorkload);
        workingCcagents.offer(ccagentWorkload);
    }
//
//    private Long getNextCcagent(CcagentWorkload ccagentWorkload) {
//        return countdownOrdersToTake(ccagentWorkload).getId();
//    }
//
    private void countdownOrdersToTake(CcagentWorkload ccagentWorkload) {
        ccagentWorkload.setOrdersToTake(ccagentWorkload.getOrdersToTake() - 1);
    }


}
