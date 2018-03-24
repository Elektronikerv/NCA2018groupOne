import {EmployeeService} from "../../../service/emploee.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {ManagerService} from "../../../service/manager.service";
import {Calendar} from "../../../model/calendar.model";
import {DatePipe} from "@angular/common";
import {WorkingDayService} from "../../../service/workingday.service";


@Component({
  selector: 'calendar',
  templateUrl: 'calendar.component.html',
  styleUrls: ['calendar.component.css']
})
export class CalendarComponent implements OnInit {
  monthCalendar: Calendar[];
  today;
  end;
  nextMonth;
  userId;



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

  static compare(one: any, two: any) {
    return Date.parse(one) > Date.parse(two) ? 1 : (Date.parse(one) == Date.parse(two) ? 0 : -1);
  }

  check(er: string[]): boolean {
    if (!er) return;
    return er.length > 1;
  }

  getCalendar() {
    const id = +this.router.snapshot.paramMap.get('id');
    this.userId = id;
    if (this.router.snapshot.url.find(x => x.path == 'next')) {
      this.managerService.getNextMonthCalendar(id).subscribe(data => {
        this.monthCalendar = data;
        this.monthCalendar.filter(day => day.wdId).forEach(filtered => {
          filtered.isValidEnd = true;
          filtered.isValidStart = true;
        });
        this.nextMonth = true;
      });
    } else {
      this.managerService.getMonthCalendar(id).subscribe(data => {
        this.monthCalendar = data;
        this.monthCalendar.filter(day => day.wdId).forEach(filtered => {
          filtered.isValidEnd = true;
          filtered.isValidStart = true;
        });
        this.nextMonth = false;
      });
    }


  }

  isDisabledStart(id: number): boolean {
    let result = this.monthCalendar.find(x => x.id == id),
      compStartTime = CalendarComponent.compare(result.startWork, this.today),
      compNow = CalendarComponent.compare(result.day, this.today);
    if (compNow == 1) {
      return false;
    }

    return compStartTime == -1;

  }

  isDisabledEnd(id: number): boolean {
    let result = this.monthCalendar.find(x => x.id == id),
      compEndTime = CalendarComponent.compare(result.endWork, this.today),
      compNow = CalendarComponent.compare(result.day, this.today);
    if (compNow == 1) {
      return false;
    }

    return compEndTime == -1;

  }

  changeStartClicked(value: string, id: number, val: boolean) {
    let res = this.monthCalendar.find(x => x.id == id);
    res.errorsMs = null;
    res.isValidStart = val;

    if (!val) {
      console.log('invalid');
      return;
    }

    res.startWork = this.fixTimeToSave(value, id);

  }

  changeEndClicked(value: string, id: number, val: boolean) {
    let res = this.monthCalendar.find(x => x.id == id);
    res.isValidEnd = val;
    res.errorsMs = null;
    if (!val) {
      return;
    }

    res.endWork = this.fixTimeToSave(value, id);

  }

  fixTimeToSave(value: string, id: number): Date {
    console.log('value' + <string>value.toString());
    let h = parseInt(value.substring(0, 2)),
      m = parseInt(value.substring(3, 5));
    console.log(h, m);
    let res = this.monthCalendar.find(x => x.id == id);
    console.log(res);
    let year = parseInt(res.day.toString().substring(0, 4)),
      month = parseInt(res.day.toString().substring(5, 7)) - 1,
      day = parseInt(res.day.toString().substring(8, 10));
    console.log(month, day, year);
    return new Date(Date.UTC(year, month, day, h, m, 0, 0));
  }

  reset() {
    this.getCalendar();
  }

  save(id: number) {
    let result = this.monthCalendar.find(x => x.id == id);
    console.log('in save');
    if (result.wdId) {
      this.edit(id);
    } else {
      result.userId = +this.router.snapshot.paramMap.get('id');
      if (!result.wdId) {
        this.wdaysService.create(result).subscribe(
          data => {
            if (Array.isArray(data)) {
              result.errorsMs = data;
            } else {
              result.isClick = false;
              result = data;
              this.getCalendar();
            }

          }
        )
      }
    }
  }

  addWeekend(id: number) {
    this.wdaysService.delete(id).subscribe(
      data => {
        this.getCalendar();
      }
    )
  }

  edit(id: number) {
    let result = this.monthCalendar.find(x => x.id == id);
    result.userId = +this.router.snapshot.paramMap.get('id');
    this.wdaysService.update(result).subscribe(
      data => {
        if (Array.isArray(data)) {
          result.errorsMs = data;
        } else {
          result.isClick = false;
          result = data;
          this.getCalendar();
        }
      }
    )
  }
}
