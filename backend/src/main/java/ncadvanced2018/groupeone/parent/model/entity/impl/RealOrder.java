package ncadvanced2018.groupeone.parent.model.entity.impl;


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
    private LocalDateTime creationTime;
    private LocalDateTime executionTime;
    private LocalDateTime receiverAvailabilityTimeFrom;
    private LocalDateTime receiverAvailabilityTimeTo;
    private String feedback;
    private String description;
    private Order parent;
}
