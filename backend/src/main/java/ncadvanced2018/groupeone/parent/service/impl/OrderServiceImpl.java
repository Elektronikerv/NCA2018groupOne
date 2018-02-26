package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.*;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private AddressDao addressDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, AddressDao addressDao) {
        this.orderDao = orderDao;
        this.addressDao = addressDao;
    }

    @Override
    public Order create(Order order) {

        if (order == null){
            log.info("Order object is null by creating");
            throw new EntityNotFoundException("Order object is null");
        }

        User user = order.getUser();
        if (user == null){
            log.info("User object is null by creating");
            throw new EntityNotFoundException("User object is null");
        }

        OrderStatus orderStatus = order.getOrderStatus();
        if (orderStatus == null){
            log.info("Order Status is null by creation");
            throw new EntityNotFoundException("OrderStatus is null");
        }

        Address receiverAddress = order.getReceiverAddress();
        if (receiverAddress != null){
            receiverAddress = addressDao.create(receiverAddress);
            order.setReceiverAddress(receiverAddress);
        }

        Address senderAddress = order.getSenderAddress();
        if (senderAddress != null){
            senderAddress = addressDao.create(senderAddress);
            order.setReceiverAddress(senderAddress);
        }

        LocalDateTime creationTime = LocalDateTime.now();
        order.setCreationTime(creationTime);
        return order;
    }

    @Override
    public Order findById(Long id) {
        if (id <= 0){
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return orderDao.findById(id);
    }

    @Override
    public Order update(Order order) {
        if (order == null){
            log.info("Order object is null by creating");
            throw new EntityNotFoundException("Order object is null");
        }

        if (orderDao.findById(order.getId()) == null){
            log.info("No such order entity");
            throw new NoSuchEntityException("Order id is not found");
        }

        User user = order.getUser();
        if (user == null){
            log.info("User object is null by creating");
            throw new EntityNotFoundException("User object is null");
        }

        Address receiverAddress = order.getReceiverAddress();
        if (receiverAddress != null){
            receiverAddress = addressDao.create(receiverAddress);
            order.setReceiverAddress(receiverAddress);
        }

        Address senderAddress = order.getSenderAddress();
        if (senderAddress != null){
            senderAddress = addressDao.create(senderAddress);
            order.setReceiverAddress(senderAddress);
        }


        return order;
    }

    @Override
    public boolean delete(Order order) {
        if (order == null){
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
        if (id <= 0){
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        Order order = orderDao.findById(id);
        if (orderDao.findById(order.getId()) == null){
            log.info("No such order entity");
            throw new NoSuchEntityException("Order id is not found");
        }
        Address receiverAddress = order.getReceiverAddress();
        Address senderAddress = order.getSenderAddress();
        boolean isDeleted = orderDao.delete(order);
        addressDao.delete(receiverAddress);
        addressDao.delete(senderAddress);

        return isDeleted;
    }

}
