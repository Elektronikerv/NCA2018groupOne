import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {Observable} from "rxjs/Observable";
import {Calendar} from "../model/calendar.model";


const url = '/api/schedule';


@Injectable()
export class WorkingDayService {

  constructor(private http: HttpClient, private tokenService: TokenService<Calendar>) {
  }


  create(day: Calendar): Observable<Calendar> {
    return this.tokenService.post(`${url}/create/calendarDay`, day);
  }

  update(day: Calendar): Observable<Calendar> {
    return this.tokenService.put(`${url}/update/calendarDay`, day);
  }

  delete(id: number): Observable<Calendar> {
    return this.tokenService.delete(`${url}/delete/calendarDay/${id}`);
  }

  getById(id: number): Observable<Calendar> {
    return this.tokenService.get(`${url}/days/${id}`);
  }

}
