package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Role;

@Data
public class RealRole implements Role {
    private Long id;
    private String name;
    private String description;
}