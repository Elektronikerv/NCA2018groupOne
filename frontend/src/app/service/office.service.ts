import {Injectable} from "@angular/core";
import {Office} from "../model/office.model";
import {catchError, tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";

const url = '/api/office';

@Injectable()
export class OfficeService {

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

  deleteOffice(id: number): Observable<Office>{
    console.log('deleteOffice(office) id: ' + id);
    return this.http.delete(`${url}/${id}`).pipe(
      tap((office:Office) => console.log(`deleted office id=${id}`))
    );
  }

  getOfficeById(id: number): Observable<Office>{
    return this.http.get<Office>(`${url}/${id}`).pipe(
      tap((office: Office) => console.log(`fetched office id=${id}`))
    );
  }

  update(office: Office): Observable<Office>{
    console.log('upadte(office) office.name: ' + office.name);
    return this.http.put(`${url}/${office.id}`, office).pipe(
      tap((office: Office) => console.log(`updated office id=${office.id}`))
    );
  }
}
