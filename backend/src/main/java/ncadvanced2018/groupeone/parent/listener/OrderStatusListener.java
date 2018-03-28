package ncadvanced2018.groupeone.parent.listener;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.event.EndOfProcessingOrderEvent;
import ncadvanced2018.groupeone.parent.event.OpeningOrderEvent;
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

    @EventListener
    public void handleOrderOpeningEvent(OpeningOrderEvent openingOrderEvent) {
        ccagentWorkloadService.executeWorkloadDistributionAfterOpening();
    }

    @EventListener
    public void handleOrderConfirmationEvent(EndOfProcessingOrderEvent endOfProcessingOrderEvent) {
        ccagentWorkloadService.executeWorkloadDistributionAfterConfirmation();
    }

}
