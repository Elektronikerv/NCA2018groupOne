package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Role;

import java.time.LocalDateTime;

@Data
public class Fulfillment {

    private Long rowNum;
    private Long fulfillmentOrderId;
    private Long ccagentId;
    private Role clientRole;
    private LocalDateTime creationTime;
}
