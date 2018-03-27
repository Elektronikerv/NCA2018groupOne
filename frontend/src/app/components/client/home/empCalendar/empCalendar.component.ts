import {ActivatedRoute, Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {DatePipe} from "@angular/common";
import {Calendar} from "../../../../model/calendar.model";
import {EmployeeService} from "../../../../service/emploee.service";
import {ManagerService} from "../../../../service/manager.service";
import {WorkingDayService} from "../../../../service/workingday.service";
import {JwtHelper} from "angular2-jwt";


@Component({
  selector: 'empCalendar',
  templateUrl: 'empCalendar.component.html',
  styleUrls: ['empCalendar.component.css']
})

export class EmpCalendarComponent implements OnInit {
  private JwtHelper: JwtHelper = new JwtHelper();
  monthCalendar: Calendar[];
  today;
  nextMonth;
  subordinateId : number;

  constructor(private employeeService: EmployeeService,
              private managerService: ManagerService,
              private wdaysService: WorkingDayService,
              private route: Router,
              private router: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getCalendar();
    this.today = Date.now();
    let datePipe = new DatePipe('en-US');
    this.today = datePipe.transform(this.today, 'yyyy-MM-dd HH:mm');
    console.log(this.today);

  }

  getCalendar() {
    let token = localStorage.getItem("currentUser");
    this.subordinateId = +this.JwtHelper.decodeToken(token).id;
    if (this.router.snapshot.url.find(x => x.path == 'next')) {
      this.managerService.getNextMonthCalendar(this.subordinateId).subscribe(data => {
        this.monthCalendar = data;
        this.monthCalendar.filter(day => day.wdId).forEach(filtered => {
          filtered.isValidEnd = true;
          filtered.isValidStart = true;
        });
        this.nextMonth = true;
      });
    } else {
      this.managerService.getMonthCalendar(this.subordinateId).subscribe(data => {
        this.monthCalendar = data;
        this.monthCalendar.filter(day => day.wdId).forEach(filtered => {
          filtered.isValidEnd = true;
          filtered.isValidStart = true;
        });
        this.nextMonth = false;
      });
    }


  }

  compare(one: any, two: any) {
    return Date.parse(one) > Date.parse(two) ? 1 : (Date.parse(one) == Date.parse(two) ? 0 : -1);
  }

  isButtonHidden(id: number) {
    let res = this.monthCalendar.find(x => x.id == id);
    if (!res.endWork) return true;
    let str = res.endWork.toString();
    return this.compare(this.today.substring(0, 10), str.substring(0, 10)) != 0 || res.workedOut;
  }

  isButtonDisabled(id: number) {
    let res = this.monthCalendar.find(x => x.id == id);
    if (!res.endWork) return true;
    return this.compare(this.today, res.endWork) < 1 || res.workedOut;
  }

  updateStatus(id: number) {
    let res = this.monthCalendar.find(x => x.id == id);
    res.workedOut = true;
    res.userId = +this.router.snapshot.paramMap.get('id');
    this.wdaysService.update(res).subscribe(data => this.getCalendar());
  }

}
