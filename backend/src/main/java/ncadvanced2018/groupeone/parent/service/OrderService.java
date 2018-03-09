package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.model.entity.*;

import java.util.List;
import java.util.Queue;

public interface OrderService {
    Order create(Order order);

    Order findById(Long id);

    Order update(Order order);

    boolean delete(Order order);

    boolean delete(Long id);

    List<Order> findAllOrders();

    List<Order> findAllOpenOrders();

    Queue <Order> findAllConfirmedOrders();

    List<OrderHistory> findByUserId(Long userId);

    FulfillmentOrder updateFulfilmentOrder(FulfillmentOrder order);

    FulfillmentOrder startProcessing(FulfillmentOrder fulfillmentOrder, Long ccagentId);

    FulfillmentOrder confirmFulfilmentOrder(FulfillmentOrder fulfillmentOrder);
}