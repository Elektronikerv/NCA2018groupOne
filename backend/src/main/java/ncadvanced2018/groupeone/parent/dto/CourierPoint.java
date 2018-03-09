package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.time.LocalDateTime;

@Data
public class CourierPoint {
    private Order order;
    private OrderAction orderAction;
    private LocalDateTime time;
}
