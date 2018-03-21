import {Component, OnInit} from '@angular/core';
import {OrderHistory} from "../../../model/orderHistory.model";
import {OrderHistoryService} from "../../../service/orderHistory.service";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderService} from "../../../service/order.service";


@Component({
  moduleId: module.id,
  selector: 'app-order-history',
  templateUrl: 'order-history.component.html',
  styleUrls: ['order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {

  orders: OrderHistory[] = [];
  user: User;
  currentUserId: number;
  sortedField = 'id';
  asc = true;
  page: number = 1;
  perPage: number = 15;

  constructor(private orderHistoryService: OrderHistoryService,
              private authService: AuthService,
              private activatedRouter: ActivatedRoute,
              private orderService: OrderService,
              private router: Router) {
    // this.authService.currentUser().subscribe((user: User) => this.user = user);


  }

  ngOnInit(): void {
    this.authService.currentUser().subscribe((user: User) => {
      this.user = user;
      this.currentUserId = user.id;
      this.getOrdersHistory();
    });
  }

  reRout(order: OrderHistory, userId: number) {
    order.senderAddress ?
    this.orderService.getOrderById(order.id, userId)
      .subscribe(() => this.router.navigate(['orderHistory/editCCOrder/'+ order.id ])) :
      this.orderService.getOrderById(order.id, userId)
        .subscribe(() => this.router.navigate(['orderHistory/editOCOrder/'+ order.id ])) ;
  }

  getOrdersHistory(): void {
    // console.log(this.user.id);
    // const id = +this.activatedRouter.snapshot.paramMap.get('id');
    // console.log('currentUserId = ' + JSON.stringify(this.currentUserId));
    this.orderService.getOrdersByUserId(this.user.id).subscribe((orders: OrderHistory[]) => {
        this.orders = orders;
        // console.log(JSON.stringify(orders[0]))
      }
    );
  }

}
