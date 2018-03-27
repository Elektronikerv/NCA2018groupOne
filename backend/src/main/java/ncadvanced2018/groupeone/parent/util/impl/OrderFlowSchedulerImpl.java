package ncadvanced2018.groupeone.parent.util.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.service.OrderService;
import ncadvanced2018.groupeone.parent.util.OrderFlowScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@PropertySource("classpath:workload.properties")
public class OrderFlowSchedulerImpl implements OrderFlowScheduler {


    private OrderService orderService;

    @Value("${orderFlow.storingDraftForDays}")
    private Long storingDraftForDays;

    @Autowired
    public OrderFlowSchedulerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @Scheduled(cron = "* * 4 * * ?")
    public void reopenUncompletedOrdersForYesterday() {
        orderService.reopenUncompletedOrdersForYesterday();
    }

    @Override
    @Scheduled(cron = "* * 4 * * ?")
    public void transitionFromDeliveredToFeedback() {
        orderService.transitionFromDeliveredToFeedback();
    }


    @Override
    @Scheduled(cron = "* * 4 * * ?")
    public void deleteObsoleteDrafts() {
        orderService.deleteObsoleteDrafts(storingDraftForDays);
    }

}
