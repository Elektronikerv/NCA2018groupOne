package ncadvanced2018.groupeone.parent.dto;

import lombok.Value;
import ncadvanced2018.groupeone.parent.model.entity.Address;

@Value
public class Edge {

    private Address origin;
    private Address destination;
    private long distanceTime;

}
