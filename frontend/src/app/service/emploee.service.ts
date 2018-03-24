import {Injectable} from "@angular/core";
import {TokenService} from "./token.service";
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {of} from "rxjs/observable/of";

const url = '/api/empl';

@Injectable()
export class EmployeeService {

  constructor(private http: HttpClient, private tokenService: TokenService<User>) {
  }

  createEmployee(employee: User): Observable<User> {
    console.log('createEmployee(employee: User) employee: ' + employee);
    return this.tokenService.post(url, employee);
  }

  getEmployees(): Observable<User[]> {
    console.log('getEmployees()');
    return this.tokenService.get(url);
  }

  getEmployeesSortedBy(sortedField:string, asc: boolean): Observable<User[]> {
    console.log('getEmployeesSortedBy(' + sortedField + ' asc = ' + asc + ')');
    const params: Array<[string, any]> = [['sortedField', sortedField], ['asc', asc]];
    return this.tokenService.getWithParams(`${url}/sort`,params);
  }

  searchEmployees(term: string): Observable<User[]> {
    if (!term.trim()) {
      return of([]);
    }
    return this.tokenService.get(`${url}/name=${term}`);
  }

  deleteEmployee(id: number): Observable<User> {
    console.log('deleteEmployee(id) id: ' + id);
    return this.tokenService.delete(`${url}/${id}`);
  }

  getEmployeeById(id: number): Observable<User> {
    return this.tokenService.get(`${url}/${id}`);
  }

  update(employee: User): Observable<User> {
    console.log('update(employee) employee.name: ' + employee.firstName);
    console.log('emplfasdgvdfuasdfksjadhbfgvboyasfdgfha: ' + JSON.stringify(employee));
    return this.tokenService.put(`${url}/${employee.id}`, employee);
  }

}
