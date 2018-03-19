package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.FulfillmentService;
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
    private FulfillmentService fulfillmentService;

    @Autowired
    public OrderController(OrderService orderService, EmployeeService employeeService, FulfillmentService fulfillmentService) {
        this.orderService = orderService;
        this.employeeService = employeeService;
        this.fulfillmentService = fulfillmentService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.create(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order byId = orderService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping("/orderHistory/{id}")
    public ResponseEntity<List<OrderHistory>> getOrderHistory(@PathVariable Long id) {
        List<OrderHistory> orderHistories = orderService.findByUserId(id);
        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> fetchOrdersAll() {
        List<Order> all = orderService.findAllOpenOrders();
        return new ResponseEntity<>(all, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody RealOrder order) {
        Order updatedOrder = orderService.update(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }


    @PostMapping("/fo/{ccagentId}")
    public ResponseEntity<FulfillmentOrder> startProcessing(@PathVariable Long ccagentId, @RequestBody FulfillmentOrder fOrder) {
        FulfillmentOrder fulfillmentOrder = orderService.startProcessing(fOrder, ccagentId);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.CREATED);
    }

    @PutMapping("/fo/confirmation")
    public ResponseEntity<FulfillmentOrder> confirmFulfillmentOrder(@RequestBody FulfillmentOrder fulfillmentOrder) {
        FulfillmentOrder order = orderService.confirmFulfilmentOrder(fulfillmentOrder);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }



    @GetMapping("/fo/{id}")
    public ResponseEntity<FulfillmentOrder> getFulfillmentOrder(@PathVariable Long id) {
        FulfillmentOrder order = fulfillmentService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping("/ccagent/{ccagentId}/fo")
    public ResponseEntity<List<FulfillmentOrder>> getFulfillmentOrders(@PathVariable Long ccagentId) {
        List<FulfillmentOrder> orders = fulfillmentService.findFulfillmentForCcagent(ccagentId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/fo")
    public ResponseEntity<FulfillmentOrder> updateFulfillmentOrder(@RequestBody FulfillmentOrder order) {
        order = orderService.updateFulfilmentOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/couriers")
    public ResponseEntity<List<User>> fetchCouriersAll() {
        List<User> couriers = employeeService.findAllEmployees();

        for (int i = 0; i < couriers.size(); i++) {
            if (!couriers.get(i).getRoles().contains(Role.COURIER)) {
                couriers.remove(i);
            }
        }
        return new ResponseEntity<>(couriers, HttpStatus.OK);
    }
}
