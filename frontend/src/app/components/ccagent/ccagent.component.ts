import { Component, OnInit } from '@angular/core';
import { OrderService } from "../../service/order.service";
import { Order } from "../../model/order.model";
import {JwtHelper} from "angular2-jwt";
import { Router } from "@angular/router";
@Component({
  selector: 'app-ccagent',
  templateUrl: './ccagent.component.html',
  styleUrls: ['./ccagent.component.css']
})
export class CcagentComponent implements OnInit {

  orders: Order[];
  private JwtHelper: JwtHelper = new JwtHelper();

  constructor(private orderService: OrderService,
              private router: Router) { }

  ngOnInit() {
    this.getOrders();
  }

  getOrders() {
      console.log('getOrders()');
      this.orderService.getOrders().subscribe((orders: Order[]) => this.orders = orders);
  }

  startProcessing(order: Order){
    let token = localStorage.getItem("currentUser");
    let ccagentId = +this.JwtHelper.decodeToken(token).id;
    this.orderService.createFulfillmentOrder(ccagentId, order)
      .subscribe((order :Order) => {
        this.router.navigate([`ccagent/orders/${order.id}`]);
        // console.log("full order : " + JSON.stringify(order.id));
      });

  }


}
