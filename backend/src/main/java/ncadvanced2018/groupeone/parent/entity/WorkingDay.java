package ncadvanced2018.groupeone.parent.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkingDay {

    private Long id;
    private User user;
    private LocalDateTime workdayStart;
    private LocalDateTime workdayEnd;
    private Boolean wordedOut;

}
