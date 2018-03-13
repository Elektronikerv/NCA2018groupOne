package ncadvanced2018.groupeone.parent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderHistory {

    private Long id;
    private OrderStatus orderStatus;
    private Address receiverAddress;
    private Address senderAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;
}
