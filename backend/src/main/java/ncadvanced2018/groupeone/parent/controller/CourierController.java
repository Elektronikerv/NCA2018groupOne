package ncadvanced2018.groupeone.parent.controller;


import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/courier")
public class CourierController {

    private CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @GetMapping("{courierId}")
    public ResponseEntity<List<FulfillmentOrder>> findFulfillmentOrdersForCourier(@PathVariable Long courierId){
        List<FulfillmentOrder> all = courierService.findFulfillmentOrdersByCourier(courierId);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("orderReceived")
    public ResponseEntity<FulfillmentOrder> orderReceived(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.orderReceived(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("isntReceived")
    public ResponseEntity<FulfillmentOrder> isntReceived(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.isntReceived(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("cancelExecution")
    public ResponseEntity<FulfillmentOrder> cancelExecution(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.cancelExecution(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("cancelDelivering")
    public ResponseEntity<FulfillmentOrder> cancelDelivering(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.cancelDelivering(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("orderDelivered")
    public ResponseEntity<FulfillmentOrder> delivered(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.orderDelivered(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("isntDelivered")
    public ResponseEntity<FulfillmentOrder> isntDelivered(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.isntDelivered(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }





}




