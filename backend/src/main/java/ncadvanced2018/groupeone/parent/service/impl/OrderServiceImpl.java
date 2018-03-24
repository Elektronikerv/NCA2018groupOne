package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dto.OrderHistory;
import ncadvanced2018.groupeone.parent.event.OrderStatusEvent;
import ncadvanced2018.groupeone.parent.event.UpdateOrderEvent;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealFulfillmentOrder;
import ncadvanced2018.groupeone.parent.service.EmployeeService;
import ncadvanced2018.groupeone.parent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private AddressDao addressDao;
    private EmployeeService employeeService;
    private FulfillmentOrderDao fulfillmentOrderDao;
    private final ApplicationEventPublisher publisher;


    @Autowired
    public OrderServiceImpl(OrderDao orderDao, AddressDao addressDao, EmployeeService employeeService, FulfillmentOrderDao fulfillmentOrderDao, ApplicationEventPublisher publisher) {
        this.orderDao = orderDao;
        this.addressDao = addressDao;
        this.employeeService = employeeService;
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.publisher = publisher;
    }

    @Override
    public Order create(Order order) {
        checkOrderBeforeCreating(order);

        order = createAddressesForOrder(order);

        LocalDateTime creationTime = LocalDateTime.now();
        order.setCreationTime(creationTime);
        order.setOrderStatus(OrderStatus.OPEN);
        Order createdOrder = orderDao.create(order);

        FulfillmentOrder fulfillmentOrder = new RealFulfillmentOrder();
        fulfillmentOrder.setOrder(createdOrder);
        fulfillmentOrder.setAttempt(1);
        fulfillmentOrderDao.create(fulfillmentOrder);

        publisher.publishEvent(new OrderStatusEvent(this, createdOrder));


        return createdOrder;
    }

    @Override
    public Order cancelOrder(Order order) {
        checkOrderBeforeCreating(order);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderDao.update(order);
    }

    @Override
    public Order createDraft(Order order) {

        checkOrderBeforeCreating(order);
        order = createAddressesForOrder(order);
        order.setOrderStatus(OrderStatus.DRAFT);
        order.setCreationTime(LocalDateTime.now());
        Order createdDraft = orderDao.create(order);
        return createdDraft;

    }

    @Override
    public Order findById(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return orderDao.findById(id);
    }

    @Override
    public List<OrderHistory> findByUserId(Long userId) {
        if (userId <= 0) {
            log.info("Illegal user id");
            throw new IllegalArgumentException();
        }
        List<Order> orders = orderDao.findByUserId(userId);
        List<OrderHistory> orderHistories = new ArrayList<>();
        for (Order order : orders) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setId(order.getId());
            orderHistory.setCreationTime(order.getCreationTime());
            orderHistory.setOrderStatus(order.getOrderStatus());
            orderHistory.setOffice(order.getOffice());
            orderHistory.setSenderAddress(order.getSenderAddress());
            orderHistory.setReceiverAddress(order.getReceiverAddress());
            orderHistories.add(orderHistory);
        }
        return orderHistories;
    }

    @Override
    public Order update(Order order) {
        if (order == null) {
            log.info("Order object is null by creating");
            throw new EntityNotFoundException("Order object is null");
        }

        if (orderDao.findById(order.getId()) == null) {
            log.info("No such order entity");
            throw new NoSuchEntityException("Order id is not found");
        }

        User user = order.getUser();
        if (user == null) {
            log.info("User object is null by creating");
            throw new EntityNotFoundException("User object is null");
        }
        Address receiverAddress = order.getReceiverAddress();
        addressDao.update(receiverAddress);
        order.setReceiverAddress(receiverAddress);

        Address senderAddress = order.getSenderAddress();
        if (senderAddress != null) {
            addressDao.update(senderAddress);
            order.setSenderAddress(senderAddress);
        }
        Order original = findById(order.getId());
        Order updatedOrder = orderDao.update(order);

        UpdateOrderEvent updateOrderEvent = new UpdateOrderEvent(this, original, updatedOrder);
        publisher.publishEvent(updateOrderEvent);

        return updatedOrder;
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = orderDao.findAllOrders();
        return orders;
    }

    @Override
    public List<Order> findAllOpenOrders() {
        List<Order> orders = orderDao.findAllOpenOrders();
        return orders;
    }

    @Override
    public Queue<Order> findAllConfirmedOrders() {
        Queue<Order> orders = new LinkedList<>(orderDao.findAllConfirmedOrders());
        return orders;
    }

    @Override
    public boolean delete(Order order) {
        if (order == null) {
            log.info("Order object is null by deleting");
            throw new EntityNotFoundException("Order object is null");
        }

        Address receiverAddress = order.getReceiverAddress();
        Address senderAddress = order.getSenderAddress();
        boolean isDeleted = orderDao.delete(order);
        addressDao.delete(receiverAddress);
        addressDao.delete(senderAddress);

        return isDeleted;
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        Order order = orderDao.findById(id);
        if (orderDao.findById(order.getId()) == null) {
            log.info("No such order entity");
            throw new NoSuchEntityException("Order id is not found");
        }
        if (order.getOrderStatus() != OrderStatus.DRAFT) {
            log.info("Order is not a draft. Orders with other statuses can be only canceled");
            throw new NoSuchEntityException("Order is not a draft. Orders with other statuses can be only canceled");
        }

        Address receiverAddress = order.getReceiverAddress();
        Address senderAddress = order.getSenderAddress();
        boolean isDeleted = orderDao.delete(order);
        addressDao.delete(receiverAddress);
        addressDao.delete(senderAddress);

        return isDeleted;
    }

    @Override
    public FulfillmentOrder updateFulfilmentOrder(FulfillmentOrder fulfillmentOrder) {
        checkFulfillment(fulfillmentOrder);

        User ccagent = fulfillmentOrder.getCcagent();
        User courier = fulfillmentOrder.getCourier();


        //order.setCcagent(employeeService.update(ccagent));
        //order.setCourier(employeeService.update(courier));
        fulfillmentOrder.setOrder(update(fulfillmentOrder.getOrder()));
        return fulfillmentOrderDao.update(fulfillmentOrder);
    }

    @Override
    public FulfillmentOrder startProcessing(FulfillmentOrder fulfillmentOrder, Long ccagentId) {
        fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.PROCESSING);
        fulfillmentOrder.setCcagent(employeeService.findById(ccagentId));
        fulfillmentOrder.setAttempt(1);
        return fulfillmentOrderDao.updateWithInternals(fulfillmentOrder);
    }



    @Override
    public FulfillmentOrder cancelAttempt(FulfillmentOrder fulfillmentOrder) {
        if (fulfillmentOrder.getCcagent() == null) {
            log.info("Ccagent object is null by creating");
            throw new EntityNotFoundException("Ccagent object is null");
        }

        checkFulfillment(fulfillmentOrder);
        fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.OPEN);
        FulfillmentOrder newFulfilmentOrder = new RealFulfillmentOrder();
        newFulfilmentOrder.setOrder(fulfillmentOrder.getOrder());
        newFulfilmentOrder.setAttempt(fulfillmentOrder.getAttempt() + 1);
        fulfillmentOrderDao.create(newFulfilmentOrder);
        return updateFulfilmentOrder(newFulfilmentOrder);
    }



    @Override
    public FulfillmentOrder cancelFulfilmentOrder(FulfillmentOrder fulfillmentOrder) {
        if (fulfillmentOrder.getCcagent() == null) {
            log.info("Ccagent object is null by creating");
            throw new EntityNotFoundException("Ccagent object is null");
        }

        checkFulfillment(fulfillmentOrder);
        fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.CANCELLED);
        return updateFulfilmentOrder(fulfillmentOrder);
    }

    @Override
    public FulfillmentOrder confirmFulfilmentOrder(FulfillmentOrder fulfillmentOrder) {
        if (fulfillmentOrder.getCcagent() == null) {
            log.info("Ccagent object is null by creating");
            throw new EntityNotFoundException("Ccagent object is null");
        }

        fulfillmentOrder.setConfirmationTime(LocalDateTime.now());

        Order oldOrder = orderDao.findById(fulfillmentOrder.getOrder().getId());

        fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.CONFIRMED);

        FulfillmentOrder updatedFulfillmentOrder = fulfillmentOrderDao.updateWithInternals(fulfillmentOrder);
        publisher.publishEvent(new OrderStatusEvent(this, updatedFulfillmentOrder.getOrder()));

        log.info("Confirmed order " + fulfillmentOrder.getOrder().getId() + ". Status from " +
                oldOrder.getOrderStatus() + " to " + updatedFulfillmentOrder.getOrder().getOrderStatus().toString());
        UpdateOrderEvent updateOrderEvent = new UpdateOrderEvent(this, oldOrder,
                updatedFulfillmentOrder.getOrder());
        publisher.publishEvent(updateOrderEvent);
        return updatedFulfillmentOrder;
    }

    @Override
    public Order findOrderForUser(Long userId, Long orderId) {
        return orderDao.findOrderForUser(userId, orderId);
    }

    private void checkFulfillment(FulfillmentOrder order) {
        if (order == null) {
            log.info("FulfillmentOrder object is null by creating");
            throw new EntityNotFoundException("Order object is null");
        }

        if (orderDao.findById(order.getOrder().getId()) == null) {
            log.info("No such order entity");
            throw new NoSuchEntityException("Order id is not found");
        }

    }

    private void checkOrderBeforeCreating(Order order) {
        if (order == null) {
            log.info("Order object is null by creating");
            throw new EntityNotFoundException("Order object is null");
        }

        User user = order.getUser();
        if (user == null) {
            log.info("User object is null by creating");
            throw new EntityNotFoundException("User object is null");
        }
    }

    private Order createAddressesForOrder(Order order) {
        Address receiverAddress = order.getReceiverAddress();
        if (receiverAddress != null) {
            receiverAddress = addressDao.create(receiverAddress);
            order.setReceiverAddress(receiverAddress);
        }

        Address senderAddress = order.getSenderAddress();
        if (senderAddress != null && senderAddress.getStreet() != null) {
            senderAddress = addressDao.create(senderAddress);
            order.setSenderAddress(senderAddress);
        }
        return order;
    }


}
