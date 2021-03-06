import {Injectable} from "@angular/core";
import {Office} from "../model/office.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";

const url = '/api/office';

@Injectable()
export class OfficeService {

  constructor(private http: HttpClient, private tokenService: TokenService<Office>) {
  }

  createOffice(office: Office): Observable<Office> {
    console.log('createOffice(office: Office) office: ' + office);
    return this.tokenService.post(url, office);
  }

  getOffices(): Observable<Office[]> {
    console.log('getOffices()');
    return this.tokenService.get(url);
  }

  getAllOffices(): Observable<Office[]> {
    console.log('getOffices()');
    return this.tokenService.get(`${url}/admin`);
  }

  getOfficesSortedBy(sortedField: String, asc: boolean): Observable<Office[]> {
    console.log("Get offices sorted by " + sortedField + " in asc=" + asc + " order");
    return this.tokenService.get(`${url}/sort?sortedField=${sortedField}&asc=${asc}`);
  }

  deactivateOffice(office : Office): Observable<Office> {
    // console.log('deactivateOffice(id) id: ' + id);
    return this.tokenService.put(`${url}/deactivate`, office);
  }

  activateOffice(office : Office): Observable<Office>{
    return this.tokenService.put(`${url}/activate`, office);
  }

  getOfficeById(id: number): Observable<Office> {
    return this.tokenService.get(`${url}/${id}`);
  }

  update(office: Office): Observable<Office> {
    console.log('update(office) office.name: ' + office.name);
    return this.tokenService.put(`${url}/${office.id}`, office);
  }
}
