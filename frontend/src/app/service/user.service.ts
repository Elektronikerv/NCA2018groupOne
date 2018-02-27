import {Injectable} from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {TokenService} from "./token.service";

const url = '/api/users';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UserService {
  constructor(private http: HttpClient, private tokenService: TokenService<User>) {
  }

  create(user: User): Observable<User> {
    console.log("service: " + user);
    return this.http.post<User>(url, user);
  }

  update(user: User): Observable<User>{
    console.log('update(user User), user service: ' + user);
    return this.tokenService.put(url, user);
  }

  updateUserInfo(user: User): Observable<User>{
    console.log('update(user User), user service: ' + user);
    return this.tokenService.put(`${url}/update`, user);
  }



  getUser(id: number): Observable<User> {
    console.log('ID: ' + id);
    return this.tokenService.get(`${url}/${id}`);
  }

}
