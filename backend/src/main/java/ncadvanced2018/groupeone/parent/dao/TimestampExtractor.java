package ncadvanced2018.groupeone.parent.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

}


