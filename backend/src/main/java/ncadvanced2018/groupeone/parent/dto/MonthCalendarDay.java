package ncadvanced2018.groupeone.parent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ncadvanced2018.groupeone.parent.util.LocalDateDeserializer;
import ncadvanced2018.groupeone.parent.validator.annotation.IntervalBetweenWorkingTimes;
import ncadvanced2018.groupeone.parent.validator.annotation.OrderWorkingTimes;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@JsonDeserialize(as = MonthCalendarDay.class)
@IntervalBetweenWorkingTimes
@OrderWorkingTimes
public class MonthCalendarDay {
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime startWork;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endWork;
    private boolean workedOut;
    private long userId;
    private long wdId;
}
