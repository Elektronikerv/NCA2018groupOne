import {Injectable} from "@angular/core";
import {Office} from "../model/office.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";

const url = '/api/admin';

@Injectable()
export class AdminService{
  office: Office;

  constructor(private http: HttpClient){}

  createOffice(office: Office): Observable<Office>{
    console.log('createOffice(office: Office) office: ' + office);
    return this.http.post<Office>(url, office);
  }

  getOffices():Observable<Office[]>{
    console.log('getOffices()');
    return this.http.get<Office[]>(url)
      .pipe(
        tap(offices => console.log(`fetched offices`))
      );
  }

}
