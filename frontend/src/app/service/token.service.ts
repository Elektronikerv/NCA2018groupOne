import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TokenService<T> {

  constructor(private http: HttpClient) {
  }

  get(url: string): Observable<T> {
    let token = localStorage.getItem('currentUser');

    console.log('get(url), token TokenService: ' + token);

    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    console.log('get(), httpOptions: ' + headers);
    return this.http.get<T>(url, {headers: headers});
  }

}
