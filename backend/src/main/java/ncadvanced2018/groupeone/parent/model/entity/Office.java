package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOffice;

@JsonDeserialize(as = RealOffice.class)
public interface Office {

    Long getId();

    void setId(Long id);

    Address getAddress();

    void setAddress(Address address);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Boolean getIsActive();

    void setIsActive(Boolean isActive);
}