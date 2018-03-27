package ncadvanced2018.groupeone.parent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;

@Data
public class OrderStatistic {
    private Long weekNumber;
    private Long gottenOrders;
    private Long processedCCA;
    private Long processedCourier;
    private Long cancelledOrders;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime avgTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime delayTime;
    private Double lvlOfService;
    private Double cancelledPercent;
}
