package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.OrderStatusEvent;
import ncadvanced2018.groupeone.parent.service.impl.CcagentWorkloadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderStatusListener {

    private CcagentWorkloadServiceImpl ccagentWorkloadService;

    @Autowired
    public OrderStatusListener(CcagentWorkloadServiceImpl ccagentWorkloadService) {
        this.ccagentWorkloadService = ccagentWorkloadService;
    }

    @EventListener(condition = "#orderStatusEvent.isOpenStatus()")
    public void handleOrderOpeningEvent(OrderStatusEvent orderStatusEvent) {
        ccagentWorkloadService.executeWorkloadDistributionAfterOpening();
    }

    @EventListener(condition = "#orderStatusEvent.isConfirmedStatus()")
    public void handleOrderConfirmationEvent(OrderStatusEvent orderStatusEvent) {
        ccagentWorkloadService.executeWorkloadDistributionAfterConfirmation();
    }

}
