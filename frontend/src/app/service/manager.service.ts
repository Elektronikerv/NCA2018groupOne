import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {GeneralStatistic} from "../model/generalStatistic.model";
import {UserStatistic} from "../model/userStatistic.model";


const url = '/api/manager';

@Injectable()
export class ManagerService {

  constructor(private http: HttpClient, private tokenService: TokenService<User>) {
  }

  getEmployees(managerId: number): Observable<User[]> {
    console.log('getEmployees()');
    return this.tokenService.get(`${url}/manager/${managerId}`);
  }

  getGeneralCCAgentStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralStatisticsByCompany()');
    return this.tokenService.get(`${url}/manager/general/ccagent/company/${startDate}/${endDate}`);
  }

  getGeneralCCAgentStatisticByManager(managerId: number, startDate: string, endDate: string): Observable<GeneralStatistic> {
    return this.tokenService.get(`${url}/manager/general/ccagent/${managerId}/${startDate}/${endDate}`);
  }

  getCCAgentStatistic(managerId: number, startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.get(`${url}/manager/ccagent/${managerId}/${startDate}/${endDate}`);
  }

  getGeneralCourierStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralStatisticsByCompany()');
    return this.tokenService.get(`${url}/manager/general/courier/company/${startDate}/${endDate}`);
  }

  getGeneralCourierStatisticByManager(managerId: number, startDate: string, endDate: string): Observable<GeneralStatistic> {
    return this.tokenService.get(`${url}/manager/general/courier/${managerId}/${startDate}/${endDate}`);
  }

  getCourierStatistic(managerId: number, startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.get(`${url}/manager/courier/${managerId}/${startDate}/${endDate}`);
  }

  getGeneralClientStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralClientStatisticsByCompany()');
    return this.tokenService.get(`${url}/manager/general/client/company/${startDate}/${endDate}`);
  }

  getClientStatistic(startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.get(`${url}/manager/stat/client/${startDate}/${endDate}`);
  }

  getGeneralOfficeStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralClientStatisticsByCompany()');
    return this.tokenService.get(`${url}/manager/general/office/company/${startDate}/${endDate}`);
  }

  getOfficeStatistic(startDate: string, endDate: string): Observable<UserStatistic[]> {
    return this.tokenService.get(`${url}/manager/stat/office/${startDate}/${endDate}`);
  }

  getCountOrdersByCCAgentInCurrentMonth(id: number): Observable<number> {
    return this.tokenService.get(`${url}/manager/ccagent/${id}/orders`);
  }

  getCountOrdersByCourierInCurrentMonth(id: number): Observable<number> {
    return this.tokenService.get(`${url}/manager/courier/${id}/orders`);
  }
}
