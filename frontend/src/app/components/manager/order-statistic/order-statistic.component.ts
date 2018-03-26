import { Component, OnInit } from '@angular/core';
import {ManagerService} from "../../../service/manager.service";
import {OrderStatisticModel} from "../../../model/orderStatistic.model";

@Component({
  selector: 'app-order-statistic',
  templateUrl: './order-statistic.component.html',
  styleUrls: ['./order-statistic.component.css']
})
export class OrderStatistic implements OnInit {
  orderStatistic: OrderStatisticModel[];

  constructor(private managerService: ManagerService) { }

  ngOnInit() {
    this.initOrderStatistic();
  }

  initOrderStatistic(){
    this.managerService.getOrderStatistic().subscribe((orderStatistic: OrderStatisticModel[])=>{
      this.orderStatistic = orderStatistic;
      console.log('orderStatistics: ' + JSON.stringify(this.orderStatistic));
    })
  }

}
