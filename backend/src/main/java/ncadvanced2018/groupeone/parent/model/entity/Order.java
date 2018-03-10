package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;

import java.time.LocalDateTime;

@JsonDeserialize(as = RealOrder.class)
public interface Order {

    Long getId();

    void setId(Long id);

    Office getOffice();

    void setOffice(Office office);

    User getUser();

    void setUser(User user);

    OrderStatus getOrderStatus();

    void setOrderStatus(OrderStatus orderStatus);

    Address getReceiverAddress();

    void setReceiverAddress(Address receiverAddress);

    Address getSenderAddress();

    void setSenderAddress(Address senderAddress);

    LocalDateTime getCreationTime();

    void setCreationTime(LocalDateTime creationTime);

    LocalDateTime getExecutionTime();

    void setExecutionTime(LocalDateTime executionTime);

    LocalDateTime getReceiveravAilabilityTimeTo();

    void setReceiveravAilabilityTimeTo(LocalDateTime clientTimeTo);

    LocalDateTime getReceiveravAilabilityTimeFrom();

    void setReceiveravAilabilityTimeFrom(LocalDateTime clientTimeFrom);

    String getFeedback();

    void setFeedback(String feedback);

    String getDescription();

    void setDescription(String description);

    Order getParent();

    void setParent(Order parent);
}