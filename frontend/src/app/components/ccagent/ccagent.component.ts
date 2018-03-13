import { Component, OnInit } from '@angular/core';
import { OrderService } from "../../service/order.service";
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
  ccagentId : number;


  constructor(private orderService: OrderService,
              private router: Router) { }

  ngOnInit() {
    let token = localStorage.getItem("currentUser");
    this.ccagentId = +this.JwtHelper.decodeToken(token).id;
    this.getFulfillments();
  }

  getFulfillments() {
      console.log('getFulfillmentOrders()');
      this.orderService.getFulfillmentOrders(this.ccagentId)
        .subscribe((fulfillmentOrder: FulfillmentOrder[]) => this.fulfillmentOrders = fulfillmentOrder);
  }


  processing(fulfillmentOrder: FulfillmentOrder){

    if(fulfillmentOrder.order.orderStatus == 'OPEN') {
      this.orderService.startProcessing(this.ccagentId, fulfillmentOrder)
        .subscribe((fulfillmentOrder : FulfillmentOrder) => {
          this.router.navigate([`ccagent/orders/${fulfillmentOrder.id}`]);
          // console.log("full order : " + JSON.stringify(order.id));
        });
    }
    else {
      this.router.navigate([`ccagent/orders/${fulfillmentOrder.id}`]);
    }

  }


}
