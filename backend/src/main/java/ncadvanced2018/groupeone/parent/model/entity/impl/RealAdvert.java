package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.AdvertType;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.time.LocalDateTime;

@Data
public class RealAdvert implements Advert {
    private Long id;
    private String header;
    private String text;
    private User admin;
    private AdvertType type;
    private LocalDateTime dateOfPublishing;
}
