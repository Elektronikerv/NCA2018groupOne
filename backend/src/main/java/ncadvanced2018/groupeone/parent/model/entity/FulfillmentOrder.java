package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;

import java.time.LocalDateTime;

@JsonDeserialize(as = RealFulfillmentOrder.class)
public interface FulfillmentOrder {

    Long getId();

    void setId(Long id);

    Order getOrder();

    void setOrder(Order order);

    User getCcagent();

    void setCcagent(User ccagent);

    User getCourier();

    void setCourier(User courier);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getConfirmationTime();

    void setConfirmationTime(LocalDateTime confirmationTime);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getReceivingTime();

    void setReceivingTime(LocalDateTime receivingTime);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getShippingTime();

    void setShippingTime(LocalDateTime shippingTime);

    Integer getAttempt();

    void setAttempt(Integer attempt);
}