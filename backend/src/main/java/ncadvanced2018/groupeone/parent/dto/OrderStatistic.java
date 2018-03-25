package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class OrderStatistic {
    private Long weekNumber;
    private Long gottenOrders;
    private Long processedCCA;
    private Long processedCourier;
    private Long cancelledOrders;
    private LocalTime avgTime;

    private LocalTime delayTime;
    private Double lvlOfService;
    private Double cancelledPercent;
}
