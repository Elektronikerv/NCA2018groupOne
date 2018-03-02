package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.time.LocalDateTime;

public class ProxyFulfillmentOrder implements FulfillmentOrder {

    private FulfillmentOrder realFulfillmentOrder;
    private FulfillmentOrderDao dao;
    private Long id;

    public ProxyFulfillmentOrder(FulfillmentOrderDao dao) {
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
    public Order getOrder() {
        return getRealFulfillmentOrder().getOrder();
    }

    @Override
    public void setOrder(Order order) {
        getRealFulfillmentOrder().setOrder(order);
    }

    @Override
    public User getCcagent() {
        return getRealFulfillmentOrder().getCcagent();
    }

    @Override
    public void setCcagent(User ccagent) {
        getRealFulfillmentOrder().setCcagent(ccagent);
    }

    @Override
    public User getCourier() {
        return getRealFulfillmentOrder().getCourier();
    }

    @Override
    public void setCourier(User courier) {
        getRealFulfillmentOrder().setCourier(courier);
    }

    @Override
    public LocalDateTime getConfirmationTime() {
        return getRealFulfillmentOrder().getConfirmationTime();
    }

    @Override
    public void setConfirmationTime(LocalDateTime confirmationTime) {
        getRealFulfillmentOrder().setConfirmationTime(confirmationTime);
    }

    @Override
    public LocalDateTime getShippingTime() {
        return getRealFulfillmentOrder().getShippingTime();
    }

    @Override
    public void setShippingTime(LocalDateTime shippingTime) {
        getRealFulfillmentOrder().setShippingTime(shippingTime);
    }

    @Override
    public Integer getAttempt() {
        return getRealFulfillmentOrder().getAttempt();
    }

    @Override
    public void setAttempt(Integer attempt) {
        getRealFulfillmentOrder().setAttempt(attempt);
    }

    private FulfillmentOrder getRealFulfillmentOrder() {
        if (realFulfillmentOrder == null) {
            realFulfillmentOrder = dao.findById(id);
        }
        return realFulfillmentOrder;
    }
}
