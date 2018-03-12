import { Component, OnInit } from '@angular/core';
import { OrderService } from "../../service/order.service";
import { Order } from "../../model/order.model";
import {JwtHelper} from "angular2-jwt";
import { Router } from "@angular/router";
import {User} from "../../model/user.model";
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";
@Component({
  selector: 'app-ccagent',
  templateUrl: './ccagent.component.html',
  styleUrls: ['./ccagent.component.css']
})
export class CcagentComponent implements OnInit {

  fulfillmentOrders: FulfillmentOrder[];
  private JwtHelper: JwtHelper = new JwtHelper();
  ccagent: User = <User>{};
  sortedField = 'id';
  asc = true;
  statuses = [];
  statusesString = '';

  constructor(private orderService: OrderService,
              private router: Router) { }

  ngOnInit() {
    this.getOrders();
  }

  getOrders() {
      console.log('getFulfillmentOrders()');
    let token = localStorage.getItem("currentUser");
    let ccagentId = +this.JwtHelper.decodeToken(token).id;
      this.orderService.getFulfillmentOrders(ccagentId)
        .subscribe((fulfillmentOrder: FulfillmentOrder[]) => this.fulfillmentOrders = fulfillmentOrder);
  }

  startProcessing(fulfillmentOrder: FulfillmentOrder){
    let token = localStorage.getItem("currentUser");
    let ccagentId = +this.JwtHelper.decodeToken(token).id;
    this.orderService.startProcessing(ccagentId, fulfillmentOrder)
      .subscribe((fulfillmentOrder : FulfillmentOrder) => {
        this.router.navigate([`ccagent/orders/${fulfillmentOrder.id}`]);
        // console.log("full order : " + JSON.stringify(order.id));
      });

  }

  addStatusToFilter(status): string[] {
    this.statuses.push(status);
    this.statusesString = this.statuses.join('.');
    return this.statusesString.split('.');
  }

  deleteStatusFromFilter(status): string[] {
    this.statuses.splice(this.statuses.indexOf(status), 1);
    this.statusesString = this.statuses.join('.');
    return this.statusesString.split('.').filter(status =>{return status.length>1});
  }

}
