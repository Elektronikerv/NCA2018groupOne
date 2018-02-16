package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String street;
    private String house;
    private Integer floor;
    private Integer flat;

}
