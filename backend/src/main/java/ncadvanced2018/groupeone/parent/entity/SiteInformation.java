package ncadvanced2018.groupeone.parent.entity;

import lombok.Data;

@Data
public class SiteInformation {

    private Long id;
    private String text;
    private User admin;
    private SiteInformationType type;

}
