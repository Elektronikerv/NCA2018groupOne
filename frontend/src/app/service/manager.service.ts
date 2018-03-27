import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {GeneralStatistic} from "../model/generalStatistic.model";
import {UserStatistic} from "../model/userStatistic.model";
import {EmpProfile} from "../model/empProfile.model";
import {UserService} from "./user.service";
import {MonthStatistic} from "../model/monthStatistic";
import {Calendar} from "../model/calendar.model";
import {OrderStatisticModel} from "../model/orderStatistic.model";


const url = '/api/manager';

@Injectable()
export class ManagerService {

  constructor(private http: HttpClient, private tokenService: TokenService<User>) {
  }

  getEmployees(managerId: number): Observable<EmpProfile[]> {
    console.log('getEmployees()');
    let arr: Array<[string, any]> = [['managerId', managerId]];
    return this.tokenService.getWithParams(`${url}/my/employees`, arr);
  }


  getEmployeesByLastName(managerId: number, lastName: string): Observable<EmpProfile[]> {
    console.log('getEmployees()');
    let arr: Array<[string, any]> = [['managerId', managerId]];
    return this.tokenService.getWithParams(`${url}/my/employees/lastName/${lastName}`, arr);
  }

  getGeneralCCAgentStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralStatisticsByCompany()');
    let arr: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getWithParams(`${url}/general/ccagent`, arr);
  }

  getGeneralCCAgentStatisticByManager(managerId: number, startDate: string, endDate: string): Observable<GeneralStatistic> {
    let arr: Array<[string, any]> = [['startDate', startDate], ['endDate', endDate], ['managerId', managerId]];
    return this.tokenService.getWithParams(`${url}/my/general/ccagent`, arr);
  }

  getCCAgentStatistic(managerId: number, startDate: string, endDate: string): Observable<UserStatistic[]> {
    let arr: Array<[string, any]> = [['startDate', startDate], ['endDate', endDate], ['managerId', managerId]];
    return this.tokenService.getWithParams(`${url}/my/personal/ccagent`, arr);
  }

  getGeneralCourierStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralStatisticsByCompany()');
    let arr: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getWithParams(`${url}/general/courier`, arr);
  }

  getGeneralCourierStatisticByManager(managerId: number, startDate: string, endDate: string): Observable<GeneralStatistic> {
    let arr: Array<[string, any]> = [['startDate', startDate], ['endDate', endDate], ['managerId', managerId]];
    return this.tokenService.getWithParams(`${url}/my/general/courier`, arr);
  }

  getCourierStatistic(managerId: number, startDate: string, endDate: string): Observable<UserStatistic[]> {
    let arr: Array<[string, any]> = [['startDate', startDate], ['endDate', endDate], ['managerId', managerId]];
    return this.tokenService.getWithParams(`${url}/my/personal/courier`, arr);
  }

  getGeneralClientStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralClientStatisticsByCompany()');
    let arr: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getWithParams(`${url}/general/client`, arr);
  }

  getClientStatistic(startDate: string, endDate: string): Observable<UserStatistic[]> {
    let arr: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getWithParams(`${url}/personal/client`, arr);
  }

  getGeneralOfficeStatisticByCompany(startDate: string, endDate: string): Observable<GeneralStatistic> {
    console.log('getGeneralClientStatisticsByCompany()');
    let arr: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getWithParams(`${url}/general/office`, arr);
  }

  getOfficeStatistic(startDate: string, endDate: string): Observable<UserStatistic[]> {
    let arr: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getWithParams(`${url}/personal/office`, arr);
  }

  getCountOrdersByCCAgentInCurrentMonth(id: number): Observable<number> {
    let arr: Array<[string, any]> = [['ccagentId', id]];
    return this.tokenService.getWithParams(`${url}/ccagent/orders`, arr);
  }

  getCountOrdersByCourierInCurrentMonth(id: number): Observable<number> {
    let arr: Array<[string, any]> = [['courierId', id]];
    return this.tokenService.getWithParams(`${url}/courier/orders`, arr);
  }

  changeClientStatusToVIP(usersStatistics: UserStatistic[]): Observable<User[]> {
    const users: User[] = [];
    console.log(users);
    usersStatistics.forEach(x => {
        let user = UserService.getEmptyUser();
        user.id = x.id;
        users.push(user);
        console.log(users);
      }
    );
    return this.tokenService.putMany(`${url}/status/client/vip`, users);
  }

  changeClientStatusToClient(usersStatistics: UserStatistic[]): Observable<User[]> {
    const users: User[] = [];
    console.log(users);
    usersStatistics.forEach(x => {
        let user = UserService.getEmptyUser();
        user.id = x.id;
        users.push(user);
        console.log(users);
      }
    );
    return this.tokenService.putMany(`${url}/status/client/client`, users);
  }

  getYearStatistics(id: number): Observable<MonthStatistic[]> {
    let arr: Array<[string, any]> = [['empId', id]];
    return this.tokenService.getWithParams(`${url}/emp`, arr);
  }

  getManagers(): Observable<User[]>{
    return this.tokenService.get(url);
  }

  getManager(employeeId: number):Observable<User>{
    return this.tokenService.get(`${url}/mgr/${employeeId}`);
  }

  getMonthCalendar(employeeId: number): Observable<Calendar[]> {
    let arr: Array<[string, number]> = [['userId', employeeId]];
    return this.tokenService.getWithParams(`${url}/month/calendar`, arr);
  }

  getNextMonthCalendar(employeeId: number): Observable<Calendar[]> {
    let arr: Array<[string, number]> = [['userId', employeeId]];
    return this.tokenService.getWithParams(`${url}/next/month/calendar`, arr);
  }

  getOrderStatistic(): Observable<OrderStatisticModel[]>{
    return this.tokenService.get(`${url}/orderStatistic`);
  }

}
