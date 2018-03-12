import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {GeneralStatistic} from "../model/generalStatistic.model";
import {UserStatistic} from "../model/userStatistic.model";
import {EmpProfile} from "../model/empProfile.model";


const url = '/api/manager';

@Injectable()
export class ManagerService {

  constructor(private http: HttpClient, private tokenService: TokenService<User>) {
  }

  getEmployees(managerId: number): Observable<EmpProfile[]> {
    console.log('getEmployees()');
    return this.tokenService.get(`${url}/${managerId}`);
  }

  getGeneralCCAgentStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralStatisticsByCompany()');
    return this.tokenService.getWithParams(`${url}/general/ccagent`, ['startDate', startDate], ['endDate', endDate]);
  }

  getGeneralCCAgentStatisticByManager(managerId: number, startDate: string, endDate: string): Observable<GeneralStatistic> {
    return this.tokenService.getWithParams(`${url}/${managerId}/general/ccagent`, ['startDate', startDate], ['endDate', endDate]);
  }

  getCCAgentStatistic(managerId: number, startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.getWithParams(`${url}/${managerId}/personal/ccagent`, ['startDate', startDate], ['endDate', endDate]);
  }

  getGeneralCourierStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralStatisticsByCompany()');
    return this.tokenService.getWithParams(`${url}/general/courier`, ['startDate', startDate], ['endDate', endDate]);
  }

  getGeneralCourierStatisticByManager(managerId: number, startDate: string, endDate: string): Observable<GeneralStatistic> {
    return this.tokenService.getWithParams(`${url}/${managerId}/general/courier`, ['startDate', startDate], ['endDate', endDate]);
  }

  getCourierStatistic(managerId: number, startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.getWithParams(`${url}/${managerId}/personal/courier`, ['startDate', startDate], ['endDate', endDate]);
  }

  getGeneralClientStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralClientStatisticsByCompany()');
    return this.tokenService.getWithParams(`${url}/general/client`, ['startDate', startDate], ['endDate', endDate]);
  }

  getClientStatistic(startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.getWithParams(`${url}/personal/client`, ['startDate', startDate], ['endDate', endDate]);
  }

  getGeneralOfficeStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralClientStatisticsByCompany()');
    return this.tokenService.getWithParams(`${url}/general/office`, ['startDate', startDate], ['endDate', endDate]);
  }

  getOfficeStatistic(startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.getWithParams(`${url}/personal/office`, ['startDate', startDate], ['endDate', endDate]);
  }

  getCountOrdersByCCAgentInCurrentMonth(id: number): Observable<number> {
    return this.tokenService.get(`${url}/${id}/ccagent/orders`);
  }

  getCountOrdersByCourierInCurrentMonth(id: number): Observable<number> {
    return this.tokenService.get(`${url}/${id}/courier/orders`);
  }
}
