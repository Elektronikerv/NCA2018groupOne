import {Component, OnInit} from '@angular/core';
import {MonthStatistic} from "../../../model/monthStatistic";
import {ManagerService} from "../../../service/manager.service";
import {AuthService} from "../../../service/auth.service";
import {ActivatedRoute} from "@angular/router";


@Component({
  moduleId: module.id,
  selector: 'yearStatistic',
  templateUrl: 'yearStatistic.component.html',
  styleUrls: ['yearStatistic.component.css']
})

export class YearStatisticComponent implements OnInit {
  monthStatistics: MonthStatistic[];
  sortedField = 'year';
  asc = true;
  userId: number;

  constructor(private managerService: ManagerService, private authService: AuthService, private router: ActivatedRoute) {
  }

  ngOnInit(): void {
    const id = +this.router.snapshot.paramMap.get('id');
    this.userId = id;
    this.getStatistics(id);
  }

  getStatistics(id: number): void {
    this.managerService.getYearStatistics(id).subscribe((statistics) => this.monthStatistics = statistics);
  }

}
