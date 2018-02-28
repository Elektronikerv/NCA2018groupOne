package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.model.entity.*;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order findById(Long id);

    Order update(Order order);

    boolean delete(Order order);

    boolean delete(Long id);

    List<OrderHistory> findByUserId(Long userId);
}