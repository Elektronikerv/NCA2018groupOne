import {Component, OnInit} from '@angular/core';
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";
import {CourierService} from "../../service/сourier.service";
import {ORDER_STATUSES, OrderStatus} from "../../model/orderStatus.model";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {Advert} from "../../model/advert.model";
import {Router} from "@angular/router";
import {JwtHelper} from "angular2-jwt";

@Component({
  moduleId: module.id,
  selector: 'app-courier',
  templateUrl: 'courier.component.html',
  styleUrls: ['courier.component.css']
})
export class CourierComponent implements OnInit {

  fulfillmentOrders: FulfillmentOrder[];
  courierId: number;
  private jwtHelper: JwtHelper = new JwtHelper();

  constructor(private courierService: CourierService,
              private router: Router,
              private authService: AuthService) {

  }

  ngOnInit() {
    let token = localStorage.getItem("currentUser");
    this.courierId = +this.jwtHelper.decodeToken(token).id;
    this.getFulfillmentOrders();
  }

  getFulfillmentOrders() {
    console.log('getFulfillmentOrders() with status EXECUTION and DELIVERING');
    this.courierService.getFulfillmentOrdersForCourier(this.courierId)
      .subscribe((fOrders: FulfillmentOrder[]) => this.fulfillmentOrders = fOrders);
  }


  // confirmExecution(fulfillment: FulfillmentOrder): void{
  //   console.log('confirmExecution() by courier '+ fulfillment.courier.id);
  //   this.courierService.confirmExecution(fulfillment)
  //     .subscribe((fulfillmentOrder: FulfillmentOrder) => {
  //       this.router.navigate(['courier/orders']);
  //     });
  // }

  orderReceived(fulfillment: FulfillmentOrder): void {

    console.log('orderReceived() by courier ' + fulfillment.courier.id);
    this.courierService.orderReceived(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });

  }

  isntReceived(fulfillment: FulfillmentOrder): void {

    console.log('isntReceived() by courier ' + fulfillment.courier.id);
    this.courierService.isntReceived(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });

  }

  cancelExecution(fulfillment: FulfillmentOrder): void {

    console.log('cancelExecution() by courier ' + fulfillment.courier.id);
    this.courierService.cancelExecution(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });

  }

  cancelDelivering(fulfillment: FulfillmentOrder): void {

    console.log('cancelDelivering() by courier ' + fulfillment.courier.id);
    this.courierService.cancelDelivering(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });

  }

  orderDelivered(fulfillment: FulfillmentOrder): void {

    console.log('orderDelivered() by courier ' + fulfillment.courier.id);
    this.courierService.orderDelivered(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });

  }

  isntDelivered(fulfillment: FulfillmentOrder): void {

    console.log('isntDelivered() by courier ' + fulfillment.courier.id);
    this.courierService.isntDelivered(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });

  }


}
