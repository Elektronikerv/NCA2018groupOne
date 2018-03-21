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
  start;
  end;


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
    const id = +this.router.snapshot.paramMap.get('id');
    this.managerService.getMonthCalendar(id).subscribe(data => {
      this.monthCalendar = data;
      console.log(this.monthCalendar);
    });
    console.log(this.monthCalendar);
  }

  isDisabledStart(id: number): boolean {
    var result = this.monthCalendar.find(x => x.id == id),
      compStartTime = this.compare(result.startWork, this.today),
      compNow = this.compare(result.day, this.today);
    if (compNow == 1) {
      return false;
    }

    return compStartTime == -1;

  }

  isDisabledEnd(id: number): boolean {
    var result = this.monthCalendar.find(x => x.id == id),
      compEndTime = this.compare(result.endWork, this.today),
      compNow = this.compare(result.day, this.today);
    if (compNow == 1) {
      return false;
    }

    return compEndTime == -1;

  }

  compare(one: any, two: any) {
    return Date.parse(one) > Date.parse(two) ? 1 : (Date.parse(one) == Date.parse(two) ? 0 : -1);
  }

  changeStartClicked(value: string, id: number) {
    console.log(this.start);
    var res = this.monthCalendar.find(x => x.id == id);
    res.startClicked = new Date(res.day + ' ' + value);
    res.startClicked.setMinutes(-res.startClicked.getTimezoneOffset() + res.startClicked.getMinutes());
    console.log(res.startClicked);
  }

  changeEndClicked(value: string, id: number) {
    console.log(value);
    console.log(new Date(value));
    var res = this.monthCalendar.find(x => x.id == id);
    res.endClicked = new Date(res.day + ' ' + value);
    res.endClicked.setMinutes(-res.endClicked.getTimezoneOffset() + res.endClicked.getMinutes());
    console.log(res.endClicked);
  }


  save(id: number) {
    var result = this.monthCalendar.find(x => x.id == id);

    if (result.wdId) {
      this.edit(id);
    } else {
      result.endWork = result.endClicked;
      console.log(result.endClicked);
      result.startWork = result.startClicked;
      result.userId = +this.router.snapshot.paramMap.get('id');
      if (!result.wdId) {
        this.wdaysService.create(result).subscribe(
          data => {
            this.getCalendar();
          }
        )
      }
    }
  }

  reset() {
    this.getCalendar();
  }

  edit(id: number) {
    var result = this.monthCalendar.find(x => x.id == id);
    result.userId = +this.router.snapshot.paramMap.get('id');
    result.endWork = result.endClicked;
    console.log(result.endClicked);
    result.startWork = result.startClicked;
    console.log(result);
    this.wdaysService.update(result).subscribe(
      data => {
        this.getCalendar();
      }
    )
  }

  addWeekend(id: number) {
    this.wdaysService.delete(id).subscribe(
      data => {
        this.getCalendar();
      }
    )
  }

}
