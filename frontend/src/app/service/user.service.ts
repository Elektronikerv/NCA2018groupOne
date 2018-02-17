import {Injectable} from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";

const url = '/api/users';

@Injectable()
export class UserService {
  constructor(private http: HttpClient, private tokenService: TokenService<User>) {
  }

  create(user: User): Observable<User> {
    console.log("service: " + user);
    return this.http.post<User>(url, user);
  }

  getUser(id: number): Observable<User> {
    console.log('ID: ' + id);
    return this.tokenService.get(`${url}/${id}`);
  }

}
