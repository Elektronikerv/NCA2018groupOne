package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;

@Data
public class RealOrderStatus implements OrderStatus {
    private Long id;
    private String name;
    private String description;
}
