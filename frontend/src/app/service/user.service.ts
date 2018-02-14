import { Injectable } from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";

const url = '/api/users';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }

  create(user: User): Observable<User>{
    console.log("service: " + user);
    return this.http.post<User>(url, user);
  }

  // getUserById(): Observable<User>{}

}
