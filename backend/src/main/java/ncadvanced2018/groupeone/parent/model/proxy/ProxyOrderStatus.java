package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.OrderStatusDao;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;

public class ProxyOrderStatus implements OrderStatus {
    private OrderStatus realOrderStatus;
    private OrderStatusDao dao;
    private Long id;

    public ProxyOrderStatus(OrderStatusDao dao) {
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
    public String getName() {
        return getRealOrderStatus().getName();
    }

    @Override
    public void setName(String name) {
        getRealOrderStatus().setName(name);
    }

    @Override
    public String getDescription() {
        return getRealOrderStatus().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getRealOrderStatus().setDescription(description);
    }

    private OrderStatus getRealOrderStatus() {
        if (realOrderStatus == null) {
            realOrderStatus = dao.findById(id);
        }
        return realOrderStatus;
    }
}
