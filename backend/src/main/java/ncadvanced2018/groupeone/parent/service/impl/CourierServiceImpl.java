package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CourierServiceImpl implements CourierService {

    private FulfillmentOrderDao fulfillmentOrderDao;

    @Autowired
    public CourierServiceImpl(FulfillmentOrderDao fulfillmentOrderDao) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
    }

    @Override
    public List<FulfillmentOrder> findFulfillmentOrdersByCourier( Long courierId) {
        if (courierId == null) {
            log.info("Parameter courierId is null in moment of finding  by courier");
            throw new IllegalArgumentException();
        }
        return fulfillmentOrderDao.findByCourier(courierId);
    }

    @Override
    public FulfillmentOrder orderReceived(FulfillmentOrder fulfillment){
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.DELIVERING);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder isntReceived(FulfillmentOrder fulfillment){
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillment.setCourier(null);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder cancelExecution(FulfillmentOrder fulfillment){
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder cancelDelivering(FulfillmentOrder fulfillment){
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillment.setCourier(null);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder orderDelivered(FulfillmentOrder fulfillment){
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setExecutionTime(LocalDateTime.now());
        fulfillment.getOrder().setOrderStatus(OrderStatus.DELIVERED);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder isntDelivered(FulfillmentOrder fulfillment){
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillment.setCourier(null);
        fulfillment.setAttempt(fulfillment.getAttempt()+1);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    private void checkFulfillmentOrder(FulfillmentOrder fulfillment){
        if (fulfillment == null) {
            log.info("FulfillmentOrder object is null in moment of updating");
            throw new EntityNotFoundException("FulfillmentOrder object is null");
        }
        if (fulfillmentOrderDao.findById(fulfillment.getId()) == null) {
            log.info("No such fulfillmentOrder entity");
            throw new NoSuchEntityException("FulfillmentOrder id is not found");
        }
    }

}


