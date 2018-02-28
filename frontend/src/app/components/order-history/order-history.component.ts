import {Component, OnInit} from '@angular/core';
import {OrderHistory} from "../../model/orderHistory.model";
import {OrderHistoryService} from "../../service/orderHistory.service";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";


@Component({
  moduleId: module.id,
  selector: 'app-order-history',
  templateUrl: 'order-history.component.html',
  styleUrls: ['order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {

  orders: OrderHistory[] = [];
  user: User;

  constructor(private orderHistoryService: OrderHistoryService,
              private authService: AuthService) {
    this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  ngOnInit() {
    this.getOrdersHistory();
  }

  getOrdersHistory(): void {
    console.log('getOrdersHistory for id = ' + this.user.id);
    this.orderHistoryService.getOrdersByUserId(this.user.id).subscribe((orders: OrderHistory[]) => this.orders = orders);
  }

}
