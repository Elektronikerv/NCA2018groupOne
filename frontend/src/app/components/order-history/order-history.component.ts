import {Component, OnInit} from '@angular/core';
import {OrderHistory} from "../../model/orderHistory.model";
import {OrderHistoryService} from "../../service/orderHistory.service";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute} from "@angular/router";


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
              private authService: AuthService,
              private activatedRouter: ActivatedRoute,) {
    // this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  ngOnInit() {
    this.getOrdersHistory();
  }

  getOrdersHistory(): void {
    console.log('getOrdersHistory');
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    console.log('id - ' + id);
    this.orderHistoryService.getOrdersByUserId(id).subscribe((orders: OrderHistory[]) => {
        this.orders = orders;
        console.log(JSON.stringify(orders[0]))
      }
    );
    console.log('id - ' + id);
  }

}
