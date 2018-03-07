package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order findById(Long id);

    Order update(Order order);

    boolean delete(Order order);

    boolean delete(Long id);

    List<Order> findAllOrders();

    List<Order> findAllOpenOrders();

    List<OrderHistory> findByUserId(Long userId);

    FulfillmentOrder updateFulfilmentOrder(FulfillmentOrder order);


}
