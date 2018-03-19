package ncadvanced2018.groupeone.parent.service.impl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.CourierService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static ncadvanced2018.groupeone.parent.dto.OrderAction.GIVE;
import static ncadvanced2018.groupeone.parent.dto.OrderAction.TAKE;

@Service
@Slf4j
public class CourierServiceImpl implements CourierService {

    private FulfillmentOrderDao fulfillmentOrderDao;
    private MapsService mapsService;
    private UserDao userDao;
    @Value("10")
    private Long minutesOnPoint;

    @Autowired
    public CourierServiceImpl(FulfillmentOrderDao fulfillmentOrderDao, MapsService mapsService, UserDao userDao) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.mapsService = mapsService;
        this.userDao = userDao;
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
    public List<CourierPoint> getCourierWay(Long courierId) {
        List<CourierPoint> courierWay = fulfillmentOrderDao.getCourierWay(courierId);
        courierWay.sort(Comparator.comparing(CourierPoint::getTime));
        return courierWay;
    }

    @Override
    public boolean searchCourier(Order order) {
        boolean isFindCourier = false;
        isFindCourier = searchFreeCourier(order);
        if (isFindCourier) {
            return true;
        }
        isFindCourier = searchBusyCourier(order);
        if (!isFindCourier) {
            log.info("Didn't find courier for order " + order.getId());
        }
        return isFindCourier;
    }

    private boolean searchBusyCourier(Order order) {
        boolean isFindCourier = false;

        List<User> couriers = userDao.findAllAvailableCouriers();
        if (couriers.isEmpty()) {
            return false;
        }

        couriers = getPriorityListOfBusyCouriers(order, couriers);

        for (User courier : couriers) {
            isFindCourier = putOrderToBusyCourier(courier, order);
            if (isFindCourier) {
                break;
            }
        }
        return isFindCourier;
    }

    private boolean searchFreeCourier(Order order) {
        boolean isFindCourier = false;

        List<User> couriers = userDao.findAllFreeCouriers();
        if (couriers.isEmpty()) {
            return false;
        }

        couriers = getPriorityListOfFreeCouriers(order, couriers);

        for (User courier : couriers) {
            isFindCourier = putOrderToFreeCourier(courier, order);
            if (isFindCourier) {
                break;
            }
        }

        return isFindCourier;
    }

    private List<User> getPriorityListOfFreeCouriers(Order order, List<User> couriers) {
        return getPriorityListOfCouriersByCurrentPosition(order, couriers);
    }

    private List<User> getPriorityListOfBusyCouriers(Order order, List<User> couriers) {
        return getPriorityListOfCouriersByCurrentPosition(order, couriers);
    }

    private List<User> getPriorityListOfCouriersByCurrentPosition(Order order, List<User> couriers) {
        List<Pair<Long, User>> courierTime = new ArrayList<>();
        for (User courier : couriers) {
            Long time = mapsService.getDistanceTime(courier.getCurrentPosition(), order.getSenderAddress());
            courierTime.add(new Pair<>(time, courier));
        }

        courierTime.sort(Comparator.comparingLong(Pair::getKey));

        List<User> sortedCourier = new ArrayList<>();

        for (Pair<Long, User> pair : courierTime) {
            sortedCourier.add(pair.getValue());
        }

        return sortedCourier;
    }

    private boolean putOrderToFreeCourier(User courier, Order order) {
        CourierPoint courierTakeOrderPoint = getTakePoint(order);

        CourierPoint courierGiveOrderPoint = getGivePoint(order);

        Long timeToTakePoint = mapsService.getDistanceTime(courier.getCurrentPosition(), order.getSenderAddress());
        courierTakeOrderPoint.setTime(LocalDateTime.now().plusMinutes(timeToTakePoint));


        Long timeFromTakeToGivePoint = mapsService.getDistanceTime(order.getSenderAddress(), order.getReceiverAddress());
        courierGiveOrderPoint.setTime(courierTakeOrderPoint.getTime()
                .plusMinutes(timeFromTakeToGivePoint).plusMinutes(minutesOnPoint));

        return confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);
    }

    private boolean putOrderToBusyCourier(User courier, Order order) {

        boolean isFindCourier = false;

        CourierPoint courierTakeOrderPoint = getTakePoint(order);

        CourierPoint courierGiveOrderPoint = getGivePoint(order);

        List<CourierPoint> courierWay = getCourierWay(courier.getId());

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

            setPointTime(courierWay.get(positionTakeSingle - 1), courierGiveOrderPoint);

            addDelays(courierWay.subList(positionTakeSingle, courierWay.size() - 1), delaySingleTake);

            setPointTime(courierWay.get(courierWay.size() - 2), courierGiveOrderPoint);

            isFindCourier = confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);

        } else if (positionGive == 0 && positionTake == 0) {
            courierWay.add(courierTakeOrderPoint);
            courierWay.add(courierGiveOrderPoint);

            setPointTime(courierWay.get(courierWay.size() - 3), courierTakeOrderPoint);
            setPointTime(courierTakeOrderPoint, courierGiveOrderPoint);
            isFindCourier = confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);

        } else if (positionGive != 0 && positionTake != 0) {
            courierWay.add(positionTake, courierTakeOrderPoint);
            courierWay.add(positionGive, courierGiveOrderPoint);

            setPointTime(courierWay.get(positionTake - 1), courierTakeOrderPoint);
            addDelays(courierWay.subList(positionTake + 1, positionGive), delayTake);

            setPointTime(courierWay.get(positionGive - 1), courierGiveOrderPoint);
            addDelays(courierWay.subList(positionGive + 1, courierWay.size()), (delayTake + delayGive));
            isFindCourier = confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);
        }

        return isFindCourier;
    }

    private Long countQuantityOfCurrentOrders(Long courier_id) {
        if (!Objects.isNull(courier_id)) {
            return fulfillmentOrderDao.countQuantityOfCurrentOrders(courier_id);
        }
        return 0L;
    }

    private boolean confirmCourierAssigning(CourierPoint courierTakeOrderPoint,
                                            CourierPoint courierGiveOrderPoint, User courier) {
        if (checkValidity(courier, courierTakeOrderPoint, courierGiveOrderPoint)) {
            FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao
                    .findActualFulfillmentByOrder(courierGiveOrderPoint.getOrder());
            fulfillmentOrder.setReceivingTime(courierTakeOrderPoint.getTime());
            fulfillmentOrder.setShippingTime(courierGiveOrderPoint.getTime());
            fulfillmentOrder.setCourier(courier);
            fulfillmentOrderDao.update(fulfillmentOrder);
            return true;
        } else {
            return false;
        }
    }

    private void addDelays(List<CourierPoint> courierWay, Long delayBoost) {
        for (CourierPoint point : courierWay) {
            point.setTime(point.getTime().plusMinutes(delayBoost));
        }
    }

    private boolean isTransitPossible(List<CourierPoint> listPoint, long minutes) {
        for (CourierPoint point : listPoint) {
            if (!isPointWithDelayPossible(point, minutes)) {
                return false;
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

    private void setPointTime(CourierPoint basedPoint, CourierPoint nextPoint) {
        LocalDateTime newTime = basedPoint.getTime().plusMinutes(
                mapsService.getDistanceTime(basedPoint.getAddress(), nextPoint.getAddress())).plusMinutes(10L);
        nextPoint.setTime(newTime);
    }

    private boolean isPointWithDelayPossible(CourierPoint point, Long delay) {
        boolean isPossible = false;
        LocalDateTime newTime = point.getTime().plusMinutes(delay);
        isPossible = point.getOrderAction() != GIVE ||
                isTimeBetween(newTime, point.getOrder()
                        .getReceiverAvailabilityTimeFrom(), point.getOrder().getReceiverAvailabilityTimeTo());
        return isPossible;
    }

    private boolean checkValidity(User courier, CourierPoint takePoint, CourierPoint givePoint) {
        if (Objects.isNull(takePoint.getTime())) {
            return false;
        }
        if (Objects.isNull(givePoint.getTime())) {
            return false;
        }
        if (!takePoint.getOrder().getId().equals(givePoint.getOrder().getId())) {
            return false;
        }
        Order order = givePoint.getOrder();
        if (!isTimeBetween(givePoint.getTime(), order.getReceiverAvailabilityTimeFrom(),
                order.getReceiverAvailabilityTimeTo())) {
            return false;
        }
        //Add courier calendar validate
        return true;
    }

    private CourierPoint getTakePoint(Order order) {
        CourierPoint courierPoint = new CourierPoint();
        courierPoint.setOrder(order);
        courierPoint.setOrderAction(TAKE);
        return courierPoint;
    }

    private CourierPoint getGivePoint(Order order) {
        CourierPoint courierPoint = new CourierPoint();
        courierPoint.setOrder(order);
        courierPoint.setOrderAction(GIVE);
        return courierPoint;
    }

    private boolean isTimeBetween(LocalDateTime timeBetween, LocalDateTime timeFrom, LocalDateTime timeTo) {
        return (timeBetween.isAfter(timeBetween) && timeBetween.isBefore(timeTo));
    }

    public List<User> findAllEmployees() {
        return userDao.findAllEmployees();
    }

    public List<User> findAllCouriers() {
        return userDao.findAllCouriers();
    }

    public List<User> findAllFreeCouriers() {
        return userDao.findAllFreeCouriers();
    }


}
