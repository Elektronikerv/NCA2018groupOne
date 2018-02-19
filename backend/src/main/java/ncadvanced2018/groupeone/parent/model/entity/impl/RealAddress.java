package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Address;

@Data
public class RealAddress implements Address {
    private Long id;
    private String street;
    private String house;
    private Integer floor;
    private Integer flat;
}
