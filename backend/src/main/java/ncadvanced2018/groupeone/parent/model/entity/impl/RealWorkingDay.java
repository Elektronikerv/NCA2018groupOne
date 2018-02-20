package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.WorkingDay;

import java.time.LocalDateTime;

@Data
public class RealWorkingDay implements WorkingDay {
    private Long id;
    private User user;
    private LocalDateTime workdayStart;
    private LocalDateTime workdayEnd;
    private Boolean wordedOut;
}
