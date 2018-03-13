package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.dto.OrderAction;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.CourierSearchService;
import ncadvanced2018.groupeone.parent.service.CourierService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CourierServiceImpl implements CourierService {

    private FulfillmentOrderDao fulfillmentOrderDao;
    private MapsService mapsService;
    private CourierSearchService courierSearchService;

    @Autowired
    public CourierServiceImpl(FulfillmentOrderDao fulfillmentOrderDao, MapsService mapsService, CourierSearchService courierSearchService) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.mapsService = mapsService;
        this.courierSearchService = courierSearchService;
    }

    @Override
    public List<FulfillmentOrder> findFulfillmentOrdersByCourier(Long courierId) {
        if (courierId == null) {
            log.info("Parameter courierId is null in moment of finding  by courier");
            throw new IllegalArgumentException();
        }
        return fulfillmentOrderDao.findByCourier(courierId);
    }

    @Override
    public FulfillmentOrder orderReceived(FulfillmentOrder fulfillment) {
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.DELIVERING);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder isntReceived(FulfillmentOrder fulfillment) {
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillment.setCourier(null);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder cancelExecution(FulfillmentOrder fulfillment) {
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder cancelDelivering(FulfillmentOrder fulfillment) {
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillment.setCourier(null);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder orderDelivered(FulfillmentOrder fulfillment) {
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setExecutionTime(LocalDateTime.now());
        fulfillment.getOrder().setOrderStatus(OrderStatus.DELIVERED);

//        User courier = fulfillment.getCourier();
//        courier.getOrderList().remove(courier.getOrderList().size());

        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public FulfillmentOrder isntDelivered(FulfillmentOrder fulfillment) {
        checkFulfillmentOrder(fulfillment);
        fulfillment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillment.setCourier(null);
        fulfillment.setAttempt(fulfillment.getAttempt() + 1);
        return fulfillmentOrderDao.updateWithInternals(fulfillment);
    }

    @Override
    public void putOrderToFreeCourier(User courier, Order order) {
        CourierPoint courierTakeOrderPoint = new CourierPoint();
        courierTakeOrderPoint.setOrder(order);
        courierTakeOrderPoint.setOrderAction(OrderAction.TAKE);

        long distanceToTakePoint = mapsService.getDistanceTime(courier.getCurrentPosition(), order.getSenderAddress());
        courierTakeOrderPoint.setTime(LocalDateTime.now().plusMinutes(distanceToTakePoint).plusMinutes(10L));

        CourierPoint courierGiveOrderPoint = new CourierPoint();
        courierGiveOrderPoint.setOrder(order);
        courierGiveOrderPoint.setOrderAction(OrderAction.GIVE);

        long distanceToGivePoint = mapsService.getDistanceTime(order.getSenderAddress(), order.getReceiverAddress());
        courierGiveOrderPoint.setTime(LocalDateTime.now().plusMinutes(distanceToGivePoint).plusMinutes(10L));

        FulfillmentOrder fulfillmentByOrder = fulfillmentOrderDao.findFulfillmentByOrder(order);
        fulfillmentByOrder.setReceivingTime(LocalDateTime.now().plusMinutes(distanceToTakePoint));
        fulfillmentByOrder.setShippingTime(LocalDateTime.now().plusMinutes(distanceToGivePoint));

        fulfillmentOrderDao.update(fulfillmentByOrder);
        fulfillmentByOrder.setCourier(courier);
    }

    @Override
    public void putOrderToCourier(User courier, Order order) {
        CourierPoint courierTakeOrderPoint = new CourierPoint();
        courierTakeOrderPoint.setOrder(order);
        courierTakeOrderPoint.setOrderAction(OrderAction.TAKE);

//        long distanceToTakePoint = mapsService.getDistanceTime(courier.getCurrentPosition(), order.getSenderAddress());
//        courierTakeOrderPoint.setTime(LocalDateTime.now().plusMinutes(distanceToTakePoint).plusMinutes(10L));

        CourierPoint courierGiveOrderPoint = new CourierPoint();
        courierGiveOrderPoint.setOrder(order);
        courierGiveOrderPoint.setOrderAction(OrderAction.GIVE);

//        long distanceToGivePoint = mapsService.getDistanceTime(order.getSenderAddress(), order.getReceiverAddress());
//        courierGiveOrderPoint.setTime(LocalDateTime.now().plusMinutes(distanceToGivePoint).plusMinutes(10L));

        List<CourierPoint> courierWay = courierSearchService.getCourierWay(courier.getId());
        Long delayTake = Long.MAX_VALUE;
        Long delayGive = Long.MAX_VALUE;
        Long delaySingleTake = Long.MAX_VALUE;

        Integer positionTake = 0;
        Integer positionGive = 0;
        Integer positionTakeSingle = 0;


        for (int i = 0; i < courierWay.size() - 1; i++) {
            courierWay.add(i + 1, courierTakeOrderPoint);

            Long distanceTimeBetweenFirstPoints = mapsService.getDistanceTime(courierWay.get(i).getAddress(), courierWay.get(i + 1).getAddress());//t1
            Long distanceTimeBetweenSecondPoints = mapsService.getDistanceTime(courierWay.get(i + 1).getAddress(), courierWay.get(i + 2).getAddress());//t2
            Long distanceTimeBetweenBaseWay = mapsService.getDistanceTime(courierWay.get(i).getAddress(), courierWay.get(i + 2).getAddress());//t

            Long newDelayTake = (distanceTimeBetweenFirstPoints + distanceTimeBetweenSecondPoints + 10L) - distanceTimeBetweenBaseWay;

            if ((newDelayTake < delaySingleTake) && (isTransitPossible(courierWay.subList(i + 1, courierWay.size()), newDelayTake))) {
                positionTakeSingle = i + 1;
                delaySingleTake = newDelayTake;
            }

            if (isTransitPossible(courierWay.subList(i + 1, courierWay.size()), newDelayTake) && (newDelayTake < (delayTake + delayGive))) {
                for (int j = i + 1; j < courierWay.size() - 1; j++) {
                    courierWay.add(j + 1, courierGiveOrderPoint);

                    distanceTimeBetweenFirstPoints = mapsService.getDistanceTime(courierWay.get(j).getAddress(), courierWay.get(j + 1).getAddress());//t1
                    distanceTimeBetweenSecondPoints = mapsService.getDistanceTime(courierWay.get(j + 1).getAddress(), courierWay.get(j + 2).getAddress());//t2
                    distanceTimeBetweenBaseWay = mapsService.getDistanceTime(courierWay.get(j).getAddress(), courierWay.get(j + 2).getAddress());//t

                    Long newDelayGive = (distanceTimeBetweenFirstPoints + distanceTimeBetweenSecondPoints + 10L) - distanceTimeBetweenBaseWay;

                    if ((newDelayTake + newDelayGive < delayTake + delayGive) && (isTransitPossible(courierWay.subList(j + 1, courierWay.size()), (newDelayTake + newDelayGive)))) {
                        delayTake = newDelayTake;
                        delayGive = newDelayGive;
                        positionTake = i + 1;
                        positionGive = j + 1;
                    }
                    courierWay.remove(j + 1);
                }
            }
            courierWay.remove(i + 1);
        }

        if (positionGive == 0 && positionTakeSingle != 0) {
            courierWay.add(positionTakeSingle, courierTakeOrderPoint);
            courierWay.add(courierGiveOrderPoint);

            calculateDelays(courierWay.subList(positionTakeSingle, courierWay.size()), delaySingleTake);

        } else if (positionGive == 0 && positionTake == 0) {
            courierWay.add(courierTakeOrderPoint);
            courierWay.add(courierGiveOrderPoint);
            Order newOrder = courierGiveOrderPoint.getOrder();
            FulfillmentOrder fulfillmentByOrder = fulfillmentOrderDao.findFulfillmentByOrder(newOrder);

            fulfillmentByOrder.setShippingTime(fulfillmentByOrder.getReceivingTime().plusMinutes(10L));

            fulfillmentOrderDao.update(fulfillmentByOrder);
        } else if (positionGive != 0 && positionTake != 0) {
            courierWay.add(positionTake, courierTakeOrderPoint);
            courierWay.add(positionGive, courierGiveOrderPoint);

            calculateDelays(courierWay.subList(positionTake, positionGive), delayTake);
            calculateDelays(courierWay.subList(positionGive, courierWay.size()), (delayTake + delayGive));
        }
    }

    private void calculateDelays(List<CourierPoint> courierWay, Long delayBoost) {
        for (CourierPoint point : courierWay) {
            Order order = point.getOrder();
            FulfillmentOrder fulfillmentByOrder = fulfillmentOrderDao.findFulfillmentByOrder(order);
            fulfillmentByOrder.setReceivingTime(fulfillmentByOrder.getReceivingTime().plusMinutes(delayBoost));
            fulfillmentByOrder.setShippingTime(fulfillmentByOrder.getShippingTime().plusMinutes(delayBoost));
            fulfillmentOrderDao.update(fulfillmentByOrder);
        }
    }

    private boolean isTransitPossible(List<CourierPoint> listPoint, long minutes) {
        for (CourierPoint point : listPoint) {
            if (point.getOrderAction().equals(OrderAction.GIVE)) {
                LocalDateTime newTime = point.getTime().plusMinutes(minutes);
                if (!newTime.isBefore(point.getOrder().getReceiveravAilabilityTimeTo()) && point.getOrder().getReceiveravAilabilityTimeFrom().isBefore(newTime)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkFulfillmentOrder(FulfillmentOrder fulfillment) {
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
