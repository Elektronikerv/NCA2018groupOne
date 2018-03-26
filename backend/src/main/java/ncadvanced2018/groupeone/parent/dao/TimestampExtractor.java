package ncadvanced2018.groupeone.parent.dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimestampExtractor {

    default LocalDateTime getLocalDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }

    default LocalDate getLocalDate(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }

    default LocalTime getLocalTime(Time time) {
        if (time != null) {
            return time.toLocalTime();
        }
        return null;
    }

}


