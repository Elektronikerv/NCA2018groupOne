import {Component, OnInit} from '@angular/core';
import {ManagerService} from "../../../service/manager.service";
import {OrderStatisticModel} from "../../../model/orderStatistic.model";
import {ReportService} from "../../../service/report.service";
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-order-statistic',
  templateUrl: './order-statistic.component.html',
  styleUrls: ['./order-statistic.component.css']
})
export class OrderStatistic implements OnInit {
  orderStatistic: OrderStatisticModel[];

  constructor(private managerService: ManagerService,
              private reportService: ReportService) {
  }

  ngOnInit() {
    this.initOrderStatistic();
  }

  initOrderStatistic() {
    this.managerService.getOrderStatistic().subscribe((orderStatistic: OrderStatisticModel[]) => {
      this.orderStatistic = orderStatistic;
      console.log('orderStatistics: ' + JSON.stringify(this.orderStatistic));
    })
  }

  generateOrderStatisticReport() {
    this.reportService.getOrderStatisticReport().subscribe(
      (res: any) => {
        let filename = 'OrderStatisticReport.pdf';
        FileSaver.saveAs(res, filename);
      }
    );
  }
}
