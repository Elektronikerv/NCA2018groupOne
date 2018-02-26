package ncadvanced2018.groupeone.parent.model.entity.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfPublishing;
}
