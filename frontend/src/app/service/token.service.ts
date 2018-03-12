import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TokenService<T> {

  constructor(private http: HttpClient) {
  }

  get<T>(url: string): Observable<T> {
    let token = localStorage.getItem('currentUser');
    // console.log('get(url), token TokenService: ' + token);
    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    // console.log('get(), httpOptions: ' + headers);
    return this.http.get<T>(url, {headers: headers});
  }

  getWithParams<T>(url: string, param: Array<[any, any]>): Observable<T> {
    let token = localStorage.getItem('currentUser');
    // console.log('get(url), token TokenService: ' + token);
    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    let params: HttpParams = new HttpParams();

    param.forEach(x => {
      console.log(x[0]);
      params = params.append(x[0], x[1])
    });
    // console.log('get(), httpOptions: ' + headers);
    return this.http.get<T>(url, {headers: headers, params: params});
  }

  post(url: string, entity: T):Observable<T> {
    let token = localStorage.getItem('currentUser');
    console.log('get(url), token TokenService: ' + token);
    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    console.log('get(), httpOptions: ' + headers);
    return this.http.post<T>(url, entity, {headers: headers});
  }

  put(url: string, entity: any):Observable<T>{
    let token = localStorage.getItem('currentUser');
    console.log('get(url), token TokenService: ' + token);
    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    console.log('put(), httpOptions: ' + headers);
    return this.http.put<T>(url, entity, {headers: headers});
  }

  delete(url: string){
    let token = localStorage.getItem('currentUser');
    console.log('get(url), token TokenService: ' + token);
    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    console.log('get(), httpOptions: ' + headers);
    return this.http.delete<T>(url, {headers: headers});
  }

}
