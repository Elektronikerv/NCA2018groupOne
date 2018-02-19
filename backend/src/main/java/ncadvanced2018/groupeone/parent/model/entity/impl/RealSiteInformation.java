package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformation;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformationType;
import ncadvanced2018.groupeone.parent.model.entity.User;

@Data
public class RealSiteInformation implements SiteInformation{
    private Long id;
    private String text;
    private User admin;
    private SiteInformationType type;
}
