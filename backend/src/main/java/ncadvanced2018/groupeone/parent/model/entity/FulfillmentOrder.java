package ncadvanced2018.groupeone.parent.model.entity;

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

    LocalDateTime getConfirmationTime();

    void setConfirmationTime(LocalDateTime confirmationTime);

    LocalDateTime getReceivingTime();

    void setReceivingTime(LocalDateTime receivingTime);

    LocalDateTime getShippingTime();

    void setShippingTime(LocalDateTime shippingTime);

    Integer getAttempt();

    void setAttempt(Integer attempt);
}