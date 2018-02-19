package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.ServiceDao;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.Service;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.time.LocalDateTime;

public class ProxyService implements Service {
    private Service realService;
    private ServiceDao dao;
    private Long id;

    public ProxyService(ServiceDao dao) {
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
        return getRealService().getOrder();
    }

    @Override
    public void setOrder(Order order) {
        getRealService().setOrder(order);
    }

    @Override
    public User getCcagent() {
        return getRealService().getCcagent();
    }

    @Override
    public void setCcagent(User ccagent) {
        getRealService().setCcagent(ccagent);
    }

    @Override
    public User getCourier() {
        return getRealService().getCourier();
    }

    @Override
    public void setCourier(User courier) {
        getRealService().setCourier(courier);
    }

    @Override
    public LocalDateTime getConfirmationTime() {
        return getRealService().getConfirmationTime();
    }

    @Override
    public void setConfirmationTime(LocalDateTime confirmationTime) {
        getRealService().setConfirmationTime(confirmationTime);
    }

    @Override
    public LocalDateTime getShippingTime() {
        return getRealService().getShippingTime();
    }

    @Override
    public void setShippingTime(LocalDateTime shippingTime) {
        getRealService().setShippingTime(shippingTime);
    }

    @Override
    public Integer getAttempt() {
        return getRealService().getAttempt();
    }

    @Override
    public void setAttempt(Integer attempt) {
        getRealService().setAttempt(attempt);
    }

    private Service getRealService() {
        if (realService == null) {
            realService = dao.findById(id);
        }
        return realService;
    }
}
