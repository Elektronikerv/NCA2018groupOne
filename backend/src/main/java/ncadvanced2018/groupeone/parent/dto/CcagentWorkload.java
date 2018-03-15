package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CcagentWorkload{

    private Long id;
    private Integer processingOrders;
    private LocalDateTime workdayEnd;
    private Integer dailyPerformance;

    private Integer ordersToTake = 0;
    private Integer ordersToTakeBeforeEndOfWorkingDay;


}
