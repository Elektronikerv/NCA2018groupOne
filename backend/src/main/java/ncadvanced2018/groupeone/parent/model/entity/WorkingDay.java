package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealWorkingDay;

import java.time.LocalDateTime;


@JsonDeserialize(as = RealWorkingDay.class)
public interface WorkingDay {

    Long getId();

    void setId(Long id);

    User getUser();

    void setUser(User user);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getWorkdayStart();

    void setWorkdayStart(LocalDateTime workdayStart);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getWorkdayEnd();

    void setWorkdayEnd(LocalDateTime workdayEnd);

    Boolean getWordedOut();

    void setWordedOut(Boolean wordedOut);
}
