package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealOrder;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.FulfillmentService;
import ncadvanced2018.groupeone.parent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.create(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @PostMapping("/createDraft")
    public ResponseEntity<Order> createDraft(@RequestBody Order order) {
        Order createdDraft = orderService.createDraft(order);
        return new ResponseEntity<>(createdDraft, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @PostMapping("/cancel")
    public ResponseEntity<Order> cancelOrder(@RequestBody Order order) {
        Order createdDraft = orderService.cancelOrder(order);
        return new ResponseEntity<>(createdDraft, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @GetMapping("/orderHistory")
    public ResponseEntity<List<OrderHistory>> getOrderHistories(@RequestParam Long userId) {
        List<OrderHistory> orderHistories = orderService.findByUserId(userId);
        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @DeleteMapping("/deleteDraft/:orderId")
    public ResponseEntity<Boolean> deleteDraft(@RequestParam Long orderId) {
        boolean delete = orderService.delete(orderId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @GetMapping("/orderHistory/infoCurrentOrder")
    public ResponseEntity<Order> getOrderHistory( @RequestParam("orderId") Long orderId,
    @RequestParam("userId") Long userId) {

        Order orderByUser = orderService.findOrderForUser(userId, orderId);
        return new ResponseEntity<>(orderByUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<Order>> fetchOrdersAll() {
        List<Order> all = orderService.findAllOpenOrders();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COURIER', 'CALL_CENTER_AGENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody RealOrder order) {
        Order updatedOrder = orderService.update(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CALL_CENTER_AGENT')")
    @PostMapping("/fo/{ccagentId}")
    public ResponseEntity<FulfillmentOrder> startProcessing(@PathVariable Long ccagentId, @RequestBody FulfillmentOrder fOrder) {
        FulfillmentOrder fulfillmentOrder = orderService.startProcessing(fOrder, ccagentId);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CALL_CENTER_AGENT')")
    @PutMapping("/fo/confirmation")
    public ResponseEntity<FulfillmentOrder> confirmFulfillmentOrder(@RequestBody FulfillmentOrder fulfillmentOrder) {
        FulfillmentOrder order = orderService.confirmFulfilmentOrder(fulfillmentOrder);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CALL_CENTER_AGENT')")
    @PutMapping("/fo/cancel")
    public ResponseEntity<FulfillmentOrder> cancelFulfillmentOrder(@RequestBody FulfillmentOrder fulfillmentOrder) {
        FulfillmentOrder order = orderService.cancelFulfilmentOrder(fulfillmentOrder);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }



    @PreAuthorize("hasRole('CALL_CENTER_AGENT')")
    @GetMapping("/fo/{id}")
    public ResponseEntity<FulfillmentOrder> getFulfillmentOrder(@PathVariable Long id) {
        FulfillmentOrder order = fulfillmentService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CALL_CENTER_AGENT')")
    @GetMapping("/ccagent/{ccagentId}/fo")
    public ResponseEntity<List<FulfillmentOrder>> getFulfillmentOrders(@PathVariable Long ccagentId) {
        List<FulfillmentOrder> orders = fulfillmentService.findFulfillmentForCcagent(ccagentId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CALL_CENTER_AGENT', 'COURIER')")
    @PutMapping("/fo/update")
    public ResponseEntity<FulfillmentOrder> updateFulfillmentOrder(@RequestBody FulfillmentOrder order) {
        order = orderService.updateFulfilmentOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CALL_CENTER_AGENT')")
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
