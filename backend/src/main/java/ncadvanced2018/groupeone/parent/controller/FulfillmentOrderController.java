package ncadvanced2018.groupeone.parent.controller;


import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import ncadvanced2018.groupeone.parent.service.FulfillmentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/fulfillmentOrder")
public class FulfillmentOrderController {

    private FulfillmentOrderService fulfillmentOrderService;

    @Autowired
    public FulfillmentOrderController(FulfillmentOrderService fulfillmentOrderService) {
        this.fulfillmentOrderService = fulfillmentOrderService;
    }

        @GetMapping("status/{statusId}/courier/{courierId}")
    public ResponseEntity<List<FulfillmentOrder>> findAllAdvertsWithType(@PathVariable Long statusId, @PathVariable Long courierId){
        List<FulfillmentOrder> all = fulfillmentOrderService.findFulfillmentOrderByStatusByCourier(statusId, courierId);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}




