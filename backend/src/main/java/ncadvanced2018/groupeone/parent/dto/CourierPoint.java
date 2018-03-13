package ncadvanced2018.groupeone.parent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.time.LocalDateTime;

@Data
public class CourierPoint {
    private Order order;
    private Address address;
    private OrderAction orderAction;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
