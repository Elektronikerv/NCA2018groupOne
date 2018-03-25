package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.*;
import ncadvanced2018.groupeone.parent.service.CourierService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import ncadvanced2018.groupeone.parent.service.WorkingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static ncadvanced2018.groupeone.parent.dto.OrderAction.GIVE;
import static ncadvanced2018.groupeone.parent.dto.OrderAction.TAKE;

@Service
@Slf4j
public class CourierServiceImpl implements CourierService {

    private FulfillmentOrderDao fulfillmentOrderDao;
    private MapsService mapsService;
    private WorkingDayService workingDayService;
    private UserDao userDao;
    private OrderDao orderDao;
    @Value("10")
    private Long minutesOnPoint;

    @Autowired
    public CourierServiceImpl(FulfillmentOrderDao fulfillmentOrderDao, MapsService mapsService, UserDao userDao,
                              WorkingDayService workingDayService, OrderDao orderDao) {
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.mapsService = mapsService;
        this.userDao = userDao;
        this.workingDayService = workingDayService;
        this.orderDao = orderDao;
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
    public void orderReceived(CourierPoint courierPoint) {
        FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findFulfillmentByOrder(courierPoint.getOrder());
        checkFulfillmentOrder(fulfillmentOrder);

        Order order = fulfillmentOrder.getOrder();
        order.setOrderStatus(OrderStatus.DELIVERING);
        orderDao.update(order);

        User courier = fulfillmentOrder.getCourier();
        courier.setCurrentPosition(courierPoint.getAddress());
        userDao.update(fulfillmentOrder.getCourier());
    }

    @Override
    public void cancelReceiving(CourierPoint courierPoint) {
        FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findFulfillmentByOrder(courierPoint.getOrder());

        checkFulfillmentOrder(fulfillmentOrder);

        User courier = fulfillmentOrder.getCourier();
        courier.setCurrentPosition(courierPoint.getAddress());
        userDao.update(fulfillmentOrder.getCourier());

        fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillmentOrder.setCourier(null);

        fulfillmentOrderDao.updateWithInternals(fulfillmentOrder);
    }

    @Override
    public void cancelDelivering(CourierPoint courierPoint) {
        FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findFulfillmentByOrder(courierPoint.getOrder());
        checkFulfillmentOrder(fulfillmentOrder);

        User courier = fulfillmentOrder.getCourier();
        courier.setCurrentPosition(courierPoint.getAddress());
        userDao.update(fulfillmentOrder.getCourier());

        fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.CONFIRMED);
        fulfillmentOrder.setCourier(null);
        fulfillmentOrder.setAttempt(fulfillmentOrder.getAttempt() + 1);

        fulfillmentOrderDao.updateWithInternals(fulfillmentOrder);
    }

    @Override
    public void orderDelivered(CourierPoint courierPoint) {
        FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findFulfillmentByOrder(courierPoint.getOrder());
        checkFulfillmentOrder(fulfillmentOrder);

        Order order = fulfillmentOrder.getOrder();
        order.setExecutionTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderDao.update(order);

        User courier = fulfillmentOrder.getCourier();
        courier.setCurrentPosition(courierPoint.getAddress());
        userDao.update(fulfillmentOrder.getCourier());
    }

    @Override
    public List<CourierPoint> getCourierWay(Long courierId) {
        List<CourierPoint> courierWay = fulfillmentOrderDao.getCourierWay(courierId);
        courierWay.sort(Comparator.comparing(CourierPoint::getTime));
        return courierWay;
    }

    @Override
    public void searchCourierForOrderInAnticipation() {
        List<Order> orders = orderDao.findAllConfirmedOrdersWithoutCourier();
        for (Order order : orders) {
            searchCourier(order);
        }
    }

    @Transactional
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
        List<Map.Entry<Long, User>> courierTime = new ArrayList<>();
        for (User courier : couriers) {
            Long time = mapsService.getDistanceTime(courier.getCurrentPosition(), order.getSenderAddress());
            courierTime.add(new AbstractMap.SimpleEntry<>(time, courier));
        }

        courierTime.sort(Comparator.comparingLong(Map.Entry::getKey));

        List<User> sortedCourier = new ArrayList<>();

        for (Map.Entry<Long, User> entry : courierTime) {
            sortedCourier.add(entry.getValue());
        }


        return sortedCourier;
    }

    private boolean putOrderToFreeCourier(User courier, Order order) {
        boolean isFindCourier = false;

        CourierPoint courierTakeOrderPoint = getTakePoint(order);

        CourierPoint courierGiveOrderPoint = getGivePoint(order);

        Long timeToTakePoint = mapsService.getDistanceTime(courier.getCurrentPosition(),
                Objects.isNull(order.getSenderAddress()) ? order.getOffice().getAddress() : order.getSenderAddress());
        courierTakeOrderPoint.setTime(LocalDateTime.now().plusMinutes(timeToTakePoint));


        Long timeFromTakeToGivePoint = mapsService.getDistanceTime(Objects.isNull(order.getSenderAddress()) ? order.getOffice().getAddress() : order.getSenderAddress(), order.getReceiverAddress());
        courierGiveOrderPoint.setTime(courierTakeOrderPoint.getTime()
                .plusMinutes(timeFromTakeToGivePoint).plusMinutes(minutesOnPoint));

        if (courierGiveOrderPoint.getTime().isBefore(order.getReceiverAvailabilityTimeFrom())) {
            courierGiveOrderPoint.setTime(order.getReceiverAvailabilityTimeFrom());
        }

        isFindCourier = confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);

        return isFindCourier;
    }

    private boolean putOrderToBusyCourier(User courier, Order order) {

        boolean isFindCourier = false;

        CourierPoint courierTakeOrderPoint = getTakePoint(order);

        CourierPoint courierGiveOrderPoint = getGivePoint(order);

        List<CourierPoint> courierWay = getCourierWay(courier.getId());

        Long delayAfterTakePoint = mapsService
                .getDistanceTime(courierWay.get(courierWay.size() - 1).getAddress(), courierTakeOrderPoint.getAddress()) + minutesOnPoint;
        Long delayAfterGivePoint = mapsService
                .getDistanceTime(courierTakeOrderPoint.getAddress(), courierGiveOrderPoint.getAddress()) + minutesOnPoint;
        LocalDateTime takeTime = courierWay.get(courierWay.size() - 1).getTime().plusMinutes(delayAfterTakePoint);
        LocalDateTime giveTime = takeTime.plusMinutes(delayAfterGivePoint);
        if (giveTime.isBefore(order.getReceiverAvailabilityTimeFrom())) {
            giveTime = order.getReceiverAvailabilityTimeFrom();
            delayAfterGivePoint = Duration.between(takeTime, giveTime).toMinutes();
        }
        Long delayAfterTakePointWithoutGivePoint = delayAfterGivePoint;

        Integer takePointPosition = 0;
        Integer givePointPosition = 0;
        Integer singleTakePointPosition = 0;


        for (int i = 0; i < courierWay.size() - 1; i++) {
            courierWay.add(i + 1, courierTakeOrderPoint);

            Long newDelayAfterTakePoint = differenceBetweenDirectWayAndNot(courierWay.get(i),
                    courierWay.get(i + 2), courierWay.get(i + 1));

            if ((newDelayAfterTakePoint < delayAfterTakePointWithoutGivePoint) &&
                    (isTransitPossible(courierWay.subList(i + 2, courierWay.size()), newDelayAfterTakePoint, courier))) {

                singleTakePointPosition = i + 1;
                delayAfterTakePointWithoutGivePoint = newDelayAfterTakePoint;
            }

            if ((newDelayAfterTakePoint < (delayAfterTakePoint + delayAfterGivePoint)) &&
                    isTransitPossible(courierWay.subList(i + 2, courierWay.size()), newDelayAfterTakePoint, courier)) {

                for (int j = i + 1; j < courierWay.size() - 1; j++) {
                    courierWay.add(j + 1, courierGiveOrderPoint);

                    Long newDelayAfterGivePoint = differenceBetweenDirectWayAndNot(courierWay.get(j),
                            courierWay.get(j + 2), courierWay.get(j + 1));

                    if ((newDelayAfterTakePoint + newDelayAfterGivePoint < delayAfterTakePoint + delayAfterGivePoint) &&
                            (isTransitPossible(courierWay.subList(j + 2, courierWay.size()),
                                    (newDelayAfterTakePoint + newDelayAfterGivePoint), courier))) {

                        delayAfterTakePoint = newDelayAfterTakePoint;
                        delayAfterGivePoint = newDelayAfterGivePoint;
                        takePointPosition = i + 1;
                        givePointPosition = j + 1;
                    }
                    courierWay.remove(j + 1);
                }
            }
            courierWay.remove(i + 1);
        }

        if (givePointPosition == 0 && singleTakePointPosition != 0) {
            courierWay.add(singleTakePointPosition, courierTakeOrderPoint);
            courierWay.add(courierGiveOrderPoint);

            setPointTime(courierWay.get(singleTakePointPosition - 1), courierTakeOrderPoint);
            addDelays(courierWay.subList(singleTakePointPosition + 1, courierWay.size() - 1), delayAfterTakePointWithoutGivePoint);
            setPointTime(courierWay.get(courierWay.size() - 2), courierGiveOrderPoint);

            updateFulfillmentByPoints(courierWay.subList(singleTakePointPosition + 1, courierWay.size() - 1));
            isFindCourier = confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);
        } else if (givePointPosition == 0 && singleTakePointPosition == 0) {
            courierWay.add(courierTakeOrderPoint);
            courierWay.add(courierGiveOrderPoint);

            setPointTime(courierWay.get(courierWay.size() - 3), courierTakeOrderPoint);
            setPointTime(courierTakeOrderPoint, courierGiveOrderPoint);

            isFindCourier = confirmCourierAssigning(courierTakeOrderPoint, courierGiveOrderPoint, courier);

        } else if (givePointPosition != 0 && takePointPosition != 0) {
            courierWay.add(takePointPosition, courierTakeOrderPoint);
            courierWay.add(givePointPosition, courierGiveOrderPoint);

            setPointTime(courierWay.get(takePointPosition - 1), courierTakeOrderPoint);
            addDelays(courierWay.subList(takePointPosition + 1, givePointPosition), delayAfterTakePoint);

            setPointTime(courierWay.get(givePointPosition - 1), courierGiveOrderPoint);
            if (courierGiveOrderPoint.getTime().isBefore(order.getReceiverAvailabilityTimeFrom())) {
                courierGiveOrderPoint.setTime(order.getReceiverAvailabilityTimeFrom());
            }
            addDelays(courierWay.subList(givePointPosition + 1, courierWay.size()), delayAfterTakePoint + delayAfterGivePoint);

            updateFulfillmentByPoints(courierWay.subList(takePointPosition + 1, givePointPosition));
            updateFulfillmentByPoints(courierWay.subList(givePointPosition + 1, courierWay.size()));
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

    private Long differenceBetweenDirectWayAndNot(CourierPoint pointFrom, CourierPoint pointTo, CourierPoint pointBetween) {

        Long timeFirsToSecond = mapsService.getDistanceTime(pointFrom.getAddress(), pointBetween.getAddress());
        Long timeSecondToThird = mapsService.getDistanceTime(pointBetween.getAddress(), pointTo.getAddress());
        Long timeFirstToThird = mapsService.getDistanceTime(pointFrom.getAddress(), pointTo.getAddress());

        LocalDateTime timeBetween = pointTo.getTime().plusMinutes(timeFirsToSecond);

        if (pointBetween.getOrderAction() == GIVE && timeBetween.isBefore(pointBetween.getOrder().getReceiverAvailabilityTimeFrom())) {
            timeBetween = pointBetween.getOrder().getReceiverAvailabilityTimeFrom();
            timeFirsToSecond = Duration.between(pointFrom.getTime(), timeBetween).toMinutes();
        }

        pointBetween.setTime(timeBetween);

        return timeFirsToSecond + timeSecondToThird + minutesOnPoint - timeFirstToThird;
    }

    private boolean confirmCourierAssigning(CourierPoint courierTakeOrderPoint,
                                            CourierPoint courierGiveOrderPoint, User courier) {
        if (checkValidity(courier, courierTakeOrderPoint, courierGiveOrderPoint)) {
            FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao
                    .findActualFulfillmentByOrder(courierGiveOrderPoint.getOrder());
            fulfillmentOrder.setReceivingTime(courierTakeOrderPoint.getTime());
            fulfillmentOrder.setShippingTime(courierGiveOrderPoint.getTime());
            fulfillmentOrder.setCourier(courier);
            fulfillmentOrder.getOrder().setOrderStatus(OrderStatus.EXECUTION);
            fulfillmentOrderDao.updateWithInternals(fulfillmentOrder);
            return true;
        } else {
            return false;
        }
    }

    private void updateFulfillmentByPoints(List<CourierPoint> points) {
        for (CourierPoint point : points) {
            Order order = point.getOrder();
            FulfillmentOrder fulfillmentOrder = fulfillmentOrderDao.findActualFulfillmentByOrder(order);
            if (point.getOrderAction() == TAKE) {
                fulfillmentOrder.setReceivingTime(point.getTime());
            } else {
                fulfillmentOrder.setShippingTime(point.getTime());
            }
            fulfillmentOrderDao.update(fulfillmentOrder);
        }
    }

    private void addDelays(List<CourierPoint> courierWay, Long delayBoost) {
        for (CourierPoint point : courierWay) {
            point.setTime(point.getTime().plusMinutes(delayBoost));
        }
    }

    private boolean isTransitPossible(List<CourierPoint> listPoint, long minutes, User courier) {
        for (CourierPoint point : listPoint) {
            if (!isPointWithDelayPossible(point, minutes)) {
                return false;
            }
        }
        if (!checkCourierTimeWithDelay(courier, listPoint.get(listPoint.size() - 1), minutes)) {
            return false;
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
                mapsService.getDistanceTime(basedPoint.getAddress(), nextPoint.getAddress())).plusMinutes(minutesOnPoint);
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
        List<CourierPoint> points = new ArrayList<>();
        points.add(takePoint);
        points.add(givePoint);
        if (!checkCourierTime(courier, points)) {
            return false;
        }
        return true;
    }

    private boolean checkCourierTime(User courier, List<CourierPoint> points) {
        List<WorkingDay> workingDays = workingDayService.findActualByUserId(courier.getId());
        for (WorkingDay workingDay : workingDays) {
            boolean isInTime = true;
            for (CourierPoint point : points) {
                if (!isTimeBetween(point.getTime(), workingDay.getWorkdayStart(), workingDay.getWorkdayEnd())) {
                    isInTime = false;
                }
            }
            if (isInTime) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCourierTimeWithDelay(User courier, CourierPoint point, Long delay) {
        List<WorkingDay> workingDays = workingDayService.findActualByUserId(courier.getId());
        for (WorkingDay workingDay : workingDays) {
            if (isTimeBetween(point.getTime(), workingDay.getWorkdayStart(), workingDay.getWorkdayEnd()) &&
                    isTimeBetween(point.getTime().plusMinutes(delay), workingDay.getWorkdayStart(),
                            workingDay.getWorkdayEnd())) {
                return true;
            }
        }
        return false;
    }

    private CourierPoint getTakePoint(Order order) {
        CourierPoint courierPoint = new CourierPoint();
        courierPoint.setOrder(order);
        courierPoint.setOrderAction(TAKE);
        courierPoint.setAddress(Objects.isNull(order.getSenderAddress()) ? order.getOffice().getAddress() : order.getSenderAddress());
        return courierPoint;
    }

    private CourierPoint getGivePoint(Order order) {
        CourierPoint courierPoint = new CourierPoint();
        courierPoint.setOrder(order);
        courierPoint.setOrderAction(GIVE);
        courierPoint.setAddress(order.getReceiverAddress());
        return courierPoint;
    }

    private boolean isTimeBetween(LocalDateTime timeBetween, LocalDateTime timeFrom, LocalDateTime timeTo) {
        return ((timeBetween.isAfter(timeFrom) && timeBetween.isBefore(timeTo)) || timeBetween.isEqual(timeFrom)
                || timeBetween.isEqual(timeTo));
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
