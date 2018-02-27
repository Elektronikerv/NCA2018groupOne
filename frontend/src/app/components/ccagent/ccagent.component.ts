import { Component, OnInit } from '@angular/core';
import { OrderService } from "../../service/order.service";
import { Order } from "../../model/order.model";

@Component({
  selector: 'app-ccagent',
  templateUrl: './ccagent.component.html',
  styleUrls: ['./ccagent.component.css']
})
export class CcagentComponent implements OnInit {

  orders: Order[];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.getOrders();
  }

  getOrders() {
      console.log('getOrders()');
      this.orderService.getOrders().subscribe((orders: Order[]) => this.orders = orders);
  }
}