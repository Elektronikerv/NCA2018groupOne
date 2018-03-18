package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAdvert;

import java.time.LocalDateTime;

@JsonDeserialize(as = RealAdvert.class)
public interface Advert {

    Long getId();

    void setId(Long id);

    String getText();

    void setText(String text);

    String getHeader();

    void setHeader(String header);

    User getAdmin();

    void setAdmin(User admin);

    AdvertType getType();

    void setType(AdvertType type);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getDateOfPublishing();

    void setDateOfPublishing(LocalDateTime dateOfPublishing);
}
