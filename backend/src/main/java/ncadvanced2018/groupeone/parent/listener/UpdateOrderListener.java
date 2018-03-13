package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.UpdateOrderEvent;
import ncadvanced2018.groupeone.parent.service.impl.CcagentWorkloadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateOrderListener {

    private CcagentWorkloadServiceImpl ccagentWorkloadService;

    @Autowired
    public UpdateOrderListener(CcagentWorkloadServiceImpl ccagentWorkloadService) {
        this.ccagentWorkloadService = ccagentWorkloadService;
    }

    @EventListener(condition = "#updateEvent.changedToConfirmedStatus")
    public void handleOrderCreatedEvent(UpdateOrderEvent updateEvent) {

    }

    @EventListener(condition = "#updateEvent.changedToOpenStatus")
    public void handleOrderOpeningEvent(UpdateOrderEvent updateEvent) {
        ccagentWorkloadService.executeWorkloadDistributionAfterOpening();
    }

    @EventListener(condition = "#updateEvent.changedToConfirmedStatus")
    public void handleOrderConfirmationEvent(UpdateOrderEvent updateEvent) {
        ccagentWorkloadService.executeWorkloadDistributionAfterConfirmation();
    }
}
