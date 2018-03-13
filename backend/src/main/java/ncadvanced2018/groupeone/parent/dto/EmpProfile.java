package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Role;

import java.util.Set;

@Data
public class EmpProfile {
    private long id;
    private String firstName;
    private String lastName;
    private Set <Role> roles;
    private long ccagentProcessingOrdersToday;
    private long ccagentCancelledOrConfirmedOrdersToday;
    private long courierDeliveringOrExecutionOrdersToday;
    private long courierDeliveredOrProblemOrdersToday;
    private long countWorkingDays;
}
