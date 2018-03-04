import { Component, OnInit } from '@angular/core';
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";
import {CourierService} from "../../service/сourier.service";
import {ORDER_STATUSES, OrderStatus} from "../../model/orderStatus.model";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {Advert} from "../../model/advert.model";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-courier',
  templateUrl: 'courier.component.html',
  styleUrls: ['courier.component.css']
})
export class CourierComponent implements OnInit {

  fulfillmentOrders: FulfillmentOrder[];
  currentOption: string;
  courierId: number;
  order_statuses: OrderStatus[];

  constructor(private courierService: CourierService,
              private router: Router,
              private authService: AuthService) {

    this.order_statuses = ORDER_STATUSES;
    this.currentOption = this.order_statuses[6].name;
  }

  ngOnInit() {
    this.authService.currentUser().subscribe((user: User) => this.courierId = user.id);
  }

  getConfirmedOrders(){
    this.getFulfillmentOrders(this.order_statuses[6]);
    this.currentOption = this.order_statuses[6].name;
  }

  getDeliveredOrders(){
    this.getFulfillmentOrders(this.order_statuses[7]);
    this.currentOption = this.order_statuses[7].name;
  }

  getFulfillmentOrders(orderStatus : OrderStatus) {
      console.log('getFulfillmentOrders() with status ' + orderStatus);
      this.courierService.getFulfillmentOrdersForCourier(orderStatus, this.courierId ).subscribe((fulfillmentOrders: FulfillmentOrder[]) => this.fulfillmentOrders = fulfillmentOrders);
  }

  cancelAssignment(fulfillment: FulfillmentOrder){
    console.log('cancelConfirmationToCourier() for courier ' + fulfillment.courier.id);
    console.log('cancelConfirmationToCourier() for fulfillment with Id ' + fulfillment.id);
    console.log('user1: ' + JSON.stringify(fulfillment));
    this.courierService.cancelAssignment(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
      this.router.navigate(['courier/orders']);
  });
  }

  acceptOrderForDelivering(fulfillment: FulfillmentOrder){
    console.log('acceptOrderForFulfillment() by courier ' + fulfillment.courier.id);
    this.courierService.acceptOrderForDelivering(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });
  }

  isNotDelivered(fulfillment: FulfillmentOrder){
    console.log('isNotDelivered() by courier (client has not taken delivery)'+ fulfillment.courier.id);
    this.courierService.isNotDelivered(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });
  }

  confirmExecution(fulfillment: FulfillmentOrder): void{
    console.log('confirmExecution() by courier '+ fulfillment.courier.id);
    this.courierService.confirmExecution(fulfillment)
      .subscribe((fulfillmentOrder: FulfillmentOrder) => {
        this.router.navigate(['courier/orders']);
      });
  }

  private isConfirmedPage(): boolean{
    return this.currentOption.endsWith(this.order_statuses[6].name);
  }

  private isDeliveringPage(): boolean{
    return this.currentOption.endsWith(this.order_statuses[7].name);
  }

}
