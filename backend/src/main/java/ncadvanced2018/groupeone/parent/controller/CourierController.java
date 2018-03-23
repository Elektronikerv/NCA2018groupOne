package ncadvanced2018.groupeone.parent.controller;


import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
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


    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @PutMapping("orderReceived")
    public ResponseEntity<CourierPoint> orderReceived(@RequestBody CourierPoint courierPoint){
        courierService.orderReceived(courierPoint);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @PutMapping("cancelReceiving")
    public ResponseEntity<CourierPoint> cancelReceiving(@RequestBody CourierPoint courierPoint){
        courierService.cancelReceiving(courierPoint);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @PutMapping("cancelDelivering")
    public ResponseEntity<CourierPoint> cancelDelivering(@RequestBody CourierPoint courierPoint){
        courierService.cancelDelivering(courierPoint);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @PutMapping("orderDelivered")
    public ResponseEntity<CourierPoint> orderDelivered(@RequestBody CourierPoint courierPoint){
        courierService.orderDelivered(courierPoint);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('COURIER')")
    @GetMapping("/way/{id}")
    public ResponseEntity<List<CourierPoint>> getWay(@PathVariable Long id) {
        List<CourierPoint> courierWay = courierService.getCourierWay(id);
        return new ResponseEntity<>(courierWay, HttpStatus.OK);
    }

}
