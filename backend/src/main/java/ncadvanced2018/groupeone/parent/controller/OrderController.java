package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderHistory")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderHistory>> getOrderHistory(@PathVariable Long id) {
        List<OrderHistory> orderHistories = orderService.findByUserId(id);
        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
    }


}
