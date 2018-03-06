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
@RequestMapping("/api/fulfillment")
public class CourierController {

    private CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @GetMapping("status/{statusId}/courier/{courierId}")
    public ResponseEntity<List<FulfillmentOrder>> findAllAdvertsWithType(@PathVariable Long statusId, @PathVariable Long courierId){
        List<FulfillmentOrder> all = courierService.findFulfillmentOrderByStatusByCourier(statusId, courierId);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("cancelAssignment")
    public ResponseEntity<FulfillmentOrder> cancelConfirmation(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.cancelAssignment(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("acceptForFulfillment")
    public ResponseEntity<FulfillmentOrder> acceptOrderForDelivering(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.acceptOrderForDelivering(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("cancelDelivery")
    public ResponseEntity<FulfillmentOrder> cancelDelivery(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.cancelDelivery(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("confirmExecution")
    public ResponseEntity<FulfillmentOrder> confirmExecution(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.confirmExecution(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @PutMapping("isNotDelivered")
    public ResponseEntity<FulfillmentOrder> isNotDelivered(@RequestBody FulfillmentOrder fulfillment){
        FulfillmentOrder fulfillmentOrder = courierService.isNotDelivered(fulfillment);
        return new ResponseEntity<>(fulfillmentOrder, HttpStatus.CREATED);
    }





}




