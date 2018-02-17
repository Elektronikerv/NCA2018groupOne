import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TokenService<T> {

  constructor(private http: HttpClient) {
  }

  get(url: string): Observable<T> {
    let newHeader = new HttpHeaders();
    newHeader.set('Content-Type', 'application/json');
    let token = localStorage.getItem('currentUser');
    console.log('get(url), token TokenService: ' + token);
    // if (token) {
      newHeader.set('Authorization', `Bearer ${token}`);
    // }
    console.log('url: ' + url);
    console.log('newHeader: ' + newHeader.get('Authorization'));
    return this.http.get<T>(url, {headers: newHeader});
  }

}
