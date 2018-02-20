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

  deleteOffice(id: number): Observable<Office> {
    console.log('deleteOffice(office) id: ' + id);
    return this.tokenService.delete(`${url}/${id}`);
  }

  getOfficeById(id: number): Observable<Office> {
    return this.tokenService.get(`${url}/${id}`);
  }

  update(office: Office): Observable<Office> {
    console.log('upadte(office) office.name: ' + office.name);
    return this.tokenService.put(`${url}/${office.id}`, office);
  }
}
