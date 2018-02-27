package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.model.entity.Order;
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
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOfficeById(@PathVariable Long id){
        Order byId = orderService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Order>> fetchOrdersAll(){
        List<Order> all = orderService.findAllProcessingOrders();
        return new ResponseEntity<>(all, HttpStatus.OK);

    }
}
