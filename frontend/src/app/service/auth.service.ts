import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {UserAuthParam} from "../model/userAuthParam.model";
import 'rxjs/add/operator/map'

const url='/api/auth';

@Injectable()
export class AuthService {
  public token: string;

  constructor(private http: HttpClient){}

  login(userAuthParam:UserAuthParam): Observable<Response>{
    return this.http.post<any>(url, {email: userAuthParam.email, password: userAuthParam.password})
      .map(userParam => {
        if (userParam && userParam.token) {
          localStorage.setItem('currentUser', JSON.stringify(userParam));
        }
        return userParam;
      });
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  checkSignIn(): boolean {
    return !!localStorage.getItem('currentUser'); // !! not null === return true
  }


}
