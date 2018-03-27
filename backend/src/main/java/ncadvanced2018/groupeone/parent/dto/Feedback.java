package ncadvanced2018.groupeone.parent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Feedback {

    private Long orderId;
    private String userFirstName;
    private String userLastName;
    private String feedback;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishingTime;
}
