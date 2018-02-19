package ncadvanced2018.groupeone.parent.model.entity;

import java.time.LocalDateTime;

public interface Service {

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

    LocalDateTime getShippingTime();

    void setShippingTime(LocalDateTime shippingTime);

    Integer getAttempt();

    void setAttempt(Integer attempt);
}