import { Component, OnInit } from '@angular/core';
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";
import {FulfillmentOrderService} from "../../service/fulfillmentOrder.service";
import {ORDER_STATUSES, OrderStatus} from "../../model/orderStatus.model";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";

@Component({
  moduleId: module.id,
  selector: 'app-courier',
  templateUrl: 'courier.component.html',
  styleUrls: ['courier.component.css']
})
export class CourierComponent implements OnInit {

  fulfillmentOrders: FulfillmentOrder[];
  courierId: number;
  order_statuses: OrderStatus[];

  constructor(private fulfillmentOrderService: FulfillmentOrderService,
              private authService: AuthService) {

  }

  ngOnInit() {
    this.authService.currentUser().subscribe((user: User) => this.courierId = user.id);
    this.order_statuses = ORDER_STATUSES;
    this.getServices(this.order_statuses[5]);
  }

  getServices(orderStatus : OrderStatus) {
      console.log('getServices()');
    this.courierId = 27;
      this.fulfillmentOrderService.getServicesForCourier(orderStatus, this.courierId ).subscribe((fulfillmentOrders: FulfillmentOrder[]) => this.fulfillmentOrders = fulfillmentOrders);
  }
}
