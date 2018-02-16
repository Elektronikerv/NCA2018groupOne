package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Service {

    private Long id;
    private Order order;
    private User ccagent;
    private User courier;
    private LocalDateTime confirmationTime;
    private LocalDateTime shippingTime;
    private Integer attempt;

}
