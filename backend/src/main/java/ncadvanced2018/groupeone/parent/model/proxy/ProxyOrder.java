package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.model.entity.*;

import java.time.LocalDateTime;

public class ProxyOrder implements Order {
    private Order realOrder;
    private OrderDao dao;
    private Long id;

    public ProxyOrder(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Office getOffice() {
        return getRealOrder().getOffice();
    }

    @Override
    public void setOffice(Office office) {
        getRealOrder().setOffice(office);
    }

    @Override
    public User getUser() {
        return getRealOrder().getUser();
    }

    @Override
    public void setUser(User user) {
        getRealOrder().setUser(user);
    }

    @Override
    public OrderStatus getOrderStatus() {
        return getRealOrder().getOrderStatus();
    }

    @Override
    public void setOrderStatus(OrderStatus orderStatus) {
        getRealOrder().setOrderStatus(orderStatus);
    }

    @Override
    public Address getReceiverAddress() {
        return getRealOrder().getReceiverAddress();
    }

    @Override
    public void setReceiverAddress(Address receiverAddress) {
        getRealOrder().setReceiverAddress(receiverAddress);
    }

    @Override
    public Address getSenderAddress() {
        return getRealOrder().getSenderAddress();
    }

    @Override
    public void setSenderAddress(Address senderAddress) {
        getRealOrder().setSenderAddress(senderAddress);
    }

    @Override
    public LocalDateTime getCreationTime() {
        return getRealOrder().getCreationTime();
    }

    @Override
    public void setCreationTime(LocalDateTime creationTime) {
        getRealOrder().setCreationTime(creationTime);
    }

    @Override
    public LocalDateTime getExecutionTime() {
        return getRealOrder().getExecutionTime();
    }

    @Override
    public void setExecutionTime(LocalDateTime executionTime) {
        getRealOrder().setExecutionTime(executionTime);
    }

    @Override
    public String getFeedback() {
        return getRealOrder().getFeedback();
    }

    @Override
    public void setFeedback(String feedback) {
        getRealOrder().setFeedback(feedback);
    }

    @Override
    public String getDescription() {
        return getRealOrder().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getRealOrder().setDescription(description);
    }

    @Override
    public Order getParent() {
        return getRealOrder().getParent();
    }

    @Override
    public void setParent(Order parent) {
        getRealOrder().setParent(parent);
    }

    @Override
    public LocalDateTime getReceiveravAilabilityTimeTo() {
        return getRealOrder().getReceiveravAilabilityTimeTo();
    }

    @Override
    public void setReceiveravAilabilityTimeTo(LocalDateTime clientTimeTo) {
        getRealOrder().setReceiveravAilabilityTimeTo(clientTimeTo);
    }

    @Override
    public LocalDateTime getReceiveravAilabilityTimeFrom() {
        return getRealOrder().getReceiveravAilabilityTimeFrom();
    }

    @Override
    public void setReceiveravAilabilityTimeFrom(LocalDateTime clientTimeFrom) {
        getRealOrder().setReceiveravAilabilityTimeFrom(clientTimeFrom);
    }

    private Order getRealOrder() {
        if (realOrder == null) {
            realOrder = dao.findById(id);
        }
        return realOrder;
    }
}
