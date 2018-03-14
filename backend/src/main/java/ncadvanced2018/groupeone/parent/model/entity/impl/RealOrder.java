package ncadvanced2018.groupeone.parent.model.entity.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.util.CustomDeserializerForTime;

import java.time.LocalDateTime;

@Data
public class RealOrder implements Order {
    private Long id;
    private Office office;
    private User user;
    private OrderStatus orderStatus;
    private Address receiverAddress;
    private Address senderAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime executionTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = CustomDeserializerForTime.class)
    private LocalDateTime receiverAvailabilityTimeFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = CustomDeserializerForTime.class)
    private LocalDateTime receiverAvailabilityTimeTo;
    private String feedback;
    private String description;
    private Order parent;
}
