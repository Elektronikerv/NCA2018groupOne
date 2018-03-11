import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {GeneralStatistic} from "../../../model/generalStatistic.model";
import {UserStatistic} from "../../../model/userStatistic.model";
import {ManagerService} from "../../../service/manager.service";
import {AuthService} from "../../../service/auth.service";
import {DatePipe} from '@angular/common';


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

  constructor(private managerService: ManagerService,
              private route: Router,
              private router: ActivatedRoute,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.start = '2018-02-07';
    this.today = Date.now();
    let datePipe = new DatePipe('en-US');
    this.today = datePipe.transform(this.today, 'yyyy-MM-dd')
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
    });
    this.managerService.getOfficeStatistic(this.start, this.end).subscribe(statistic => {
        console.log(statistic);
        this.userStatistics = statistic;
      }
    )
  }

  changeStart(start: any) {
    this.start = start;
    console.log(this.start);
    this.changeDate();
  }

  changeEnd(end: any) {
    this.end = end;
    console.log(this.end);
    this.changeDate();
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

}
