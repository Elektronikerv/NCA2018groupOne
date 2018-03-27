package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;

@Data
public class RealOffice implements Office {
    private Long id;
    private Address address;
    private String name;
    private String description;
    private Boolean isActive;
}