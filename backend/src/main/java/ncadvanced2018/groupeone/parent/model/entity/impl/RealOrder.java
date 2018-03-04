package ncadvanced2018.groupeone.parent.model.entity.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.*;

import java.time.LocalDateTime;

@Data
public class RealOrder implements Order {
    private Long id;
    private Office office;
    private User user;
    private OrderStatus orderStatus;
    private Address receiverAddress;
    private Address senderAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime executionTime;
    private String feedback;
    private String description;
    private Order parent;
}
