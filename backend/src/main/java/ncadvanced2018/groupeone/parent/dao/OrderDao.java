package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.util.List;

public interface OrderDao extends CrudDao <Order, Long> {
    List<Order> findAllOrders();
}
