import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {GeneralStatistic} from "../../../model/generalStatistic.model";
import {UserStatistic} from "../../../model/userStatistic.model";
import {ManagerService} from "../../../service/manager.service";
import {AuthService} from "../../../service/auth.service";
import {DatePipe} from '@angular/common';
import {ReportService} from "../../../service/report.service";
import * as FileSaver from 'file-saver';


@Component({
  selector: 'statistics',
  templateUrl: 'statistics.component.html',
  styleUrls: ['statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  generalStatisticByCompany: GeneralStatistic;
  generalStatisticByManager: GeneralStatistic;
  userStatistics: UserStatistic[];
  selectedValue: string;
  start;
  end;
  today;
  sortedField = 'id';
  asc = true;
  isNull: boolean;
  page: number = 1;
  perPage: number = 15;
  coeff: number;
  sign: any = '>';
  uncheck = false;


  constructor(private managerService: ManagerService,
              private route: Router,
              private router: ActivatedRoute,
              private authService: AuthService,
              private reportService: ReportService) {
  }

  ngOnInit(): void {
    this.start = '2018-02-07';
    this.today = Date.now();
    let datePipe = new DatePipe('en-US');
    this.today = datePipe.transform(this.today, 'yyyy-MM-dd');
    this.end = this.today;
    console.log(this.start);
    this.selectedValue = 'CCAgent';
    this.change(this.selectedValue);
    console.log('onInit()')
  }

  change(selectedValue: string) {
    console.log(selectedValue);
    this.selectedValue = selectedValue;
    switch (selectedValue) {
      case 'CCAgent': {
        this.changeOnCCAgent();
        break;
      }
      case 'Courier': {
        this.changeOnCourier();
        break;
      }
      case 'Client': {
        this.changeOnClient();
        break;
      }
      case 'Office': {
        this.changeOnOffice();
        break;
      }
    }
  }

  changeOnCCAgent() {
    console.log('CCAgent method');
    this.managerService.getGeneralCCAgentStatisticByCompany(this.start, this.end).subscribe(statistic => {
      console.log(this.start);
      console.log(this.end);
      console.log(statistic);
      this.generalStatisticByCompany = statistic;
      this.isNull = this.generalStatisticByCompany == null;
    });
    this.managerService.getGeneralCCAgentStatisticByManager(this.authService.currentUserId(),
      this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.generalStatisticByManager = statistic;
      }
    );
    this.managerService.getCCAgentStatistic(this.authService.currentUserId(),
      this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.userStatistics = statistic;
      }
    )
  }

  changeOnCourier() {
    console.log('Courier method');
    this.managerService.getGeneralCourierStatisticByCompany(this.start, this.end).subscribe(statistic => {
      console.log(statistic);
      this.generalStatisticByCompany = statistic;
      this.isNull = this.generalStatisticByCompany == null;
    });
    this.managerService.getGeneralCourierStatisticByManager(this.authService.currentUserId(),
      this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.generalStatisticByManager = statistic;
      }
    );
    this.managerService.getCourierStatistic(this.authService.currentUserId(),
      this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.userStatistics = statistic;
      }
    )
  }

  changeOnClient() {
    console.log('Client method');
    this.managerService.getGeneralClientStatisticByCompany(this.start, this.end).subscribe(statistic => {
      console.log(statistic);
      this.generalStatisticByCompany = statistic;
      this.isNull = this.generalStatisticByCompany == null;
    });
    this.managerService.getClientStatistic(this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.userStatistics = statistic;
      }
    )
  }

  changeOnOffice() {
    console.log('Office method');
    this.managerService.getGeneralOfficeStatisticByCompany(this.start, this.end).subscribe(statistic => {
      console.log(statistic);
      this.generalStatisticByCompany = statistic;
      this.isNull = this.generalStatisticByCompany == null;
    });
    this.managerService.getOfficeStatistic(this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.userStatistics = statistic;
      }
    )
  }

  changeStart(start: any) {
    if (start) {
      this.start = start;
      console.log('change');
      if (Date.parse(this.start) > Date.parse(this.end)) {
        console.log('compare');
        this.end = this.start;
      }
      console.log(this.start);
      this.changeDate();
    } else {
      this.generalStatisticByCompany = null;
      this.userStatistics = null;
    }
  }

  changeEnd(end: any) {
    if (end) {
      this.end = end;
      console.log('change');
      if (Date.parse(this.end) < Date.parse(this.start)) {
        console.log('compare');
        this.start = this.end;
      }
      console.log(this.end);
      this.changeDate();
    } else {
      this.userStatistics = null;
      this.generalStatisticByCompany = null;
    }
  }

  changeDate() {
    switch (this.selectedValue) {
      case 'CCAgent': {
        this.changeOnCCAgent();
        break;
      }
      case 'Courier': {
        this.changeOnCourier();
        break;
      }
      case 'Client': {
        this.changeOnClient();
        break;
      }
      case 'Office': {
        this.changeOnOffice();
        break;
      }
    }
  }

  changeStatusToVIP() {
    console.log('changeStatus');
    console.log(this.userStatistics.filter(x => x.checked));
    this.managerService.changeClientStatusToVIP(this.userStatistics.filter(x => x.checked)).subscribe(user => {
      user.forEach(x => {
        this.userStatistics.find(s => s.id == x.id).status = x.roles.includes('CLIENT') ? 'CLIENT' : 'VIP_CLIENT';
      });
      this.userStatistics.filter(x => x.checked).forEach(y => y.checked = false);
      this.uncheck = false;
      this.sortedField = 'id';
    });
  }

  changeStatusToClient() {
    console.log('changeStatus');
    console.log(this.userStatistics.filter(x => x.checked));
    this.managerService.changeClientStatusToClient(this.userStatistics.filter(x => x.checked)).subscribe(user => {
      user.forEach(x => {
        this.userStatistics.find(s => s.id == x.id).status = x.roles.includes('CLIENT') ? 'CLIENT' : 'VIP_CLIENT';
      });
      this.userStatistics.filter(x => x.checked).forEach(y => y.checked = false);
      this.uncheck = false;
      this.sortedField = 'id';
    });
  }

  generateManagerReports() {
    switch (this.selectedValue) {
      case 'CCAgent': {
        this.reportService.getPersonalCCAgentStatisticReport(this.authService.currentUserId(), this.start, this.end).subscribe(
          (res: any) => {
            let filename = 'Personal CCAgent statistic report from ' + this.start + ' to ' + this.end + '.pdf';
            FileSaver.saveAs(res, filename);
          }
        );
        break;
      }
      case 'Courier': {
        this.reportService.getPersonalCourierStatisticReport(this.authService.currentUserId(), this.start, this.end).subscribe(
          (res: any) => {
            let filename = 'Personal Courier statistic report from ' + this.start + ' to ' + this.end + '.pdf';
            FileSaver.saveAs(res, filename);
          }
        );
        break;
      }
      case 'Client': {
        this.reportService.getClientStatisticReport(this.start, this.end).subscribe(
          (res: any) => {
            let filename = 'Client statistic report from ' + this.start + ' to ' + this.end + '.pdf';
            FileSaver.saveAs(res, filename);
          }
        );
        break;
      }
      case 'Office': {
        this.reportService.getOfficeStatisticReport(this.start, this.end).subscribe(
          (res: any) => {
            let filename = 'Office statistic report from ' + this.start + ' to ' + this.end + '.pdf';
            FileSaver.saveAs(res, filename);
          }
        );
        break;
      }
    }

  }

  check() {
    this.unchecked();
    this.uncheck = false;
    this.asc = true;
    this.sortedField = 'id';
    if (this.coeff) {
      switch (this.sign) {
        case '>': {
          this.userStatistics.filter(x => x.differenceBetweenAvgCompany > this.coeff)
            .filter(user => user.status != 'DELETED')
            .forEach(s => s.checked = true);
          break;
        }
        case '>=': {
          this.userStatistics.filter(x => x.differenceBetweenAvgCompany >= this.coeff)
            .filter(user => user.status != 'DELETED')
            .forEach(s => s.checked = true);
          break;
        }
        case '=': {
          this.userStatistics.filter(x => x.differenceBetweenAvgCompany == this.coeff)
            .filter(user => user.status != 'DELETED')
            .forEach(s => s.checked = true);
          break;
        }
        case '<': {
          this.userStatistics.filter(x => x.differenceBetweenAvgCompany < this.coeff)
            .filter(user => user.status != 'DELETED')
            .forEach(s => s.checked = true);
          break;
        }
        case '=<': {
          this.userStatistics.filter(x => x.differenceBetweenAvgCompany <= this.coeff)
            .filter(user => user.status != 'DELETED')
            .forEach(s => s.checked = true);
          break;
        }
      }
      if (this.userStatistics.filter(x => x.checked).length > 0) {
        this.sortedField = 'checked';
        this.asc = false;
        this.uncheck = true;
      }
    }
  }

  unchecked() {
    this.userStatistics.filter(user => user.status != 'DELETED').forEach(x => x.checked = false);
  }

  checked() {
    this.userStatistics.filter(user => user.status != 'DELETED').forEach(x => x.checked = true);
  }

  changeCheckedAll() {
    this.uncheck ? this.checked() : this.unchecked();
  }

  getCountSelected(): number {
    return this.userStatistics ? this.userStatistics.filter(x => x.checked == true).length : 0;
  }

}
