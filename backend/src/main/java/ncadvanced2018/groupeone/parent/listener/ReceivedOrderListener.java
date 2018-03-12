package ncadvanced2018.groupeone.parent.listener;

import ncadvanced2018.groupeone.parent.event.ReceivedOrderEvent;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.CourierService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import ncadvanced2018.groupeone.parent.service.OrderService;
import ncadvanced2018.groupeone.parent.service.TransitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ReceivedOrderListener {

    private final OrderService orderService;
    private final MapsService mapService;
    private final CourierService courierService;
    private final TransitService transitService;

    @Autowired
    public ReceivedOrderListener(OrderService orderService, MapsService mapService, CourierService courierService, TransitService transitService) {//
        this.orderService = orderService;
        this.mapService = mapService;
        this.courierService = courierService;
        this.transitService = transitService;
    }

    @EventListener
    public void handleOrderReceivedEvent(ReceivedOrderEvent receivedOrderEvent) {
        FulfillmentOrder fulfillmentOrder = receivedOrderEvent.getFulfillmentOrder();
        Order order = fulfillmentOrder.getOrder();
        User courier = fulfillmentOrder.getCourier();

        List<Order> allOpenOrders = orderService.findAllOpenOrders();
        if (allOpenOrders.isEmpty()) {
            return;
        }

        Order nearestOrder = findNearestOrder(order, allOpenOrders);
        if(nearestOrder != null){
            courierService.putOrderToCourier(courier, nearestOrder);
        }

    }

    private Order findNearestOrder(Order baseOrder, List<Order> candidatesOpenNear){
        final Order nearestOrder = candidatesOpenNear.stream()
                .min(Comparator.comparing(order -> mapService.getDistanceTime(baseOrder.getSenderAddress(), order.getSenderAddress())))
                .get();

        if (transitService.isTransitPossible(baseOrder, nearestOrder)){

            return nearestOrder;
        }
        return null;
    }

}
