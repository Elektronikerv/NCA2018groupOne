package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformationType;

@Data
public class RealSiteInformationType implements SiteInformationType {
    private Long id;
    private String name;
}
