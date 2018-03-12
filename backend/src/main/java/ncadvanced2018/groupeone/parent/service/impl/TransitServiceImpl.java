package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dto.Edge;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.service.EdgeService;
import ncadvanced2018.groupeone.parent.service.TransitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransitServiceImpl implements TransitService {

    private final EdgeService edgeService;
    private final FulfillmentOrderDao fulfillmentOrderDao;
    private final long delayInCheckPoint = 10L;

    @Autowired
    public TransitServiceImpl(EdgeService edgeService, FulfillmentOrderDao fulfillmentOrderDao) {
        this.edgeService = edgeService;
        this.fulfillmentOrderDao = fulfillmentOrderDao;
    }

    @Override
    public boolean isTransitPossible(Order order, Order transitOrder) {
        Address senderAddressOrder = order.getSenderAddress();
        Address receiverAddressOrder = order.getReceiverAddress();

        Address senderAddressDestinationOrder = transitOrder.getSenderAddress();
        Address receiverAddressDestinationOrder = transitOrder.getReceiverAddress();


//        Edge firstOrder = edgeService.getEdge(senderAddressOrder, receiverAddressOrder);
        Edge secondOrder = edgeService.getEdge(senderAddressDestinationOrder, receiverAddressDestinationOrder);

        Edge untilTakePoint = edgeService.getEdge(senderAddressOrder, senderAddressDestinationOrder);
        Edge untilGivePoint = edgeService.getEdge(receiverAddressOrder, receiverAddressDestinationOrder);

        if (transitOrder.getReceiveravAilabilityTimeFrom().isBefore(order.getReceiveravAilabilityTimeTo().minusMinutes(untilGivePoint.getDistanceTime()).minusMinutes(delayInCheckPoint))) {
            long transitTime = untilTakePoint.getDistanceTime() + secondOrder.getDistanceTime() + untilGivePoint.getDistanceTime();

            LocalDateTime timeOfDeliveringBaseOrder = LocalDateTime.now().plusMinutes(transitTime).plusMinutes(2 * delayInCheckPoint);
            LocalDateTime timeOfDeliveringNearestOrder = LocalDateTime.now().plusMinutes(untilTakePoint.getDistanceTime() + secondOrder.getDistanceTime()).plusMinutes(delayInCheckPoint);

            FulfillmentOrder fulfillmentByOrder = fulfillmentOrderDao.findFulfillmentByOrder(order);
            fulfillmentByOrder.setReceivingTime(timeOfDeliveringBaseOrder);

            FulfillmentOrder fulfillmentByTransitOrder = fulfillmentOrderDao.findFulfillmentByOrder(transitOrder);
            fulfillmentByTransitOrder.setReceivingTime(timeOfDeliveringNearestOrder);

            fulfillmentByOrder.getCourier().getOrderDeque().addFirst(transitOrder);

            System.out.println("timeOfDeliveringFirstOrder1: " + timeOfDeliveringBaseOrder);
            System.out.println("timeOfDeliveringSecondOrder1: " + timeOfDeliveringNearestOrder);

            return LocalDateTime.now().plusMinutes(transitTime).isBefore(order.getReceiveravAilabilityTimeTo().minusMinutes(delayInCheckPoint));
        } else if (order.getReceiveravAilabilityTimeFrom().isBefore(transitOrder.getReceiveravAilabilityTimeTo().minusMinutes(untilGivePoint.getDistanceTime()).minusMinutes(delayInCheckPoint))) {
            Edge takeSecondGiveFirst = edgeService.getEdge(senderAddressDestinationOrder, receiverAddressOrder);

            long transitTime = untilTakePoint.getDistanceTime() + takeSecondGiveFirst.getDistanceTime() + untilGivePoint.getDistanceTime();

            LocalDateTime timeOfDeliveringBaseOrder = LocalDateTime.now().plusMinutes(untilTakePoint.getDistanceTime() + takeSecondGiveFirst.getDistanceTime()).plusMinutes(delayInCheckPoint);
            LocalDateTime timeOfDeliveringTransitOrder = LocalDateTime.now().plusMinutes(transitTime).plusMinutes(2 * delayInCheckPoint);

            FulfillmentOrder fulfillmentByTransitOrder = fulfillmentOrderDao.findFulfillmentByOrder(transitOrder);
            fulfillmentByTransitOrder.setReceivingTime(timeOfDeliveringTransitOrder);

            FulfillmentOrder fulfillmentByBaseOrder = fulfillmentOrderDao.findFulfillmentByOrder(order);
            fulfillmentByBaseOrder.setReceivingTime(timeOfDeliveringBaseOrder);

            fulfillmentByBaseOrder.getCourier().getOrderDeque().addLast(transitOrder);

            System.out.println("timeOfDeliveringFirstOrder2: " + timeOfDeliveringBaseOrder);
            System.out.println("timeOfDeliveringSecondOrder2: " + timeOfDeliveringTransitOrder);

            return LocalDateTime.now().plusMinutes(transitTime).isBefore(order.getReceiveravAilabilityTimeTo().minusMinutes(10L));
        }
        return false;
    }
}
