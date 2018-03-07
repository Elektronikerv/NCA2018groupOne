package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private EmployeeService employeeService;
    private FulfillmentOrderDao orderDao;

    @Autowired
    public OrderController(OrderService orderService, EmployeeService employeeService, FulfillmentOrderDao orderDao) {
        this.orderService = orderService;
        this.employeeService = employeeService;
        this.orderDao = orderDao;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order byId = orderService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping("/orderHistory/{id}")
    public ResponseEntity<List<OrderHistory>> getOrderHistory(@PathVariable Long id) {
        List<OrderHistory> orderHistories = orderService.findByUserId(id);
        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> fetchOrdersAll(){
        List<Order> all = orderService.findAllOpenOrders();
        return new ResponseEntity<>(all, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody RealOrder order) {
        Order updatedOrder = orderService.update(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/fo/{ccagentId}/{orderId}")
    public ResponseEntity<Long> createFullfilmentOrder(@PathVariable Long ccagentId, @PathVariable Long orderId) {

        FulfillmentOrder fullfilmentOrder = new RealFulfillmentOrder();
        Order order = orderService.findById(orderId);
        order.setOrderStatus(OrderStatus.PROCESSING);
        fullfilmentOrder.setOrder(order);
        fullfilmentOrder.setCcagent(employeeService.findById(ccagentId));
        fullfilmentOrder.setAttempt(0);
        fullfilmentOrder = orderDao.create(fullfilmentOrder);
        return new ResponseEntity<>(fullfilmentOrder.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/fo/{id}")
    public ResponseEntity<FulfillmentOrder> getFullfilmentOrder(@PathVariable Long id) {
        FulfillmentOrder order = orderDao.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/fo/{id}")
    public ResponseEntity<FulfillmentOrder> updateFullfilmentOrder(@RequestBody FulfillmentOrder order) {
        order = orderService.updateFulfilmentOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/couriers")
    public ResponseEntity<List<User>> fetchCouriersAll() {
        List<User> couriers = employeeService.findAllEmployees();

        for(int i=0; i < couriers.size(); i++) {
            if(!couriers.get(i).getRoles().contains(Role.COURIER))
                couriers.remove(i);
        }
        return new ResponseEntity<List<User>>(couriers, HttpStatus.OK);
    }
}
