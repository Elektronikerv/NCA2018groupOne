package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Office office;
    private User user;
    private OrderStatus orderStatus;
    private Address receiverAddress;
    private Address senderAddress;
    private LocalDateTime creationTime;
    private LocalDateTime executionTime;
    private String feedback;
    private String description;
    private Order parent;


}
