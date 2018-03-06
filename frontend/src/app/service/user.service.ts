import {Injectable} from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {Address} from "../model/address.model";

const url = '/api/users';

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
    return this.tokenService.put(`${url}/${user.id}`, user);
  }

  updateUserInfo(user: User): Observable<User>{
    console.log('update(user User), user service: ' + user);
    return this.tokenService.put(`${url}/update`, user);
  }

  getUser(id: number): Observable<User> {
    // console.log('ID: ' + id);
    return this.tokenService.get(`${url}/${id}`);
  }

  static getEmptyUser(): User{
    return {
      id: null,
      firstName: null,
      email: null,
      password: null,
      confirmPassword: null,
      address: UserService.getEmptyAddress(),
      lastName: null,
      managerId: null,
      phoneNumber: null,
      registrationDate: null,
      roles: null
    };
  }

  static getEmptyAddress(): Address{
    return {
      id: null,
      street: null,
      house: null,
      floor: null,
      flat: null,

    }; }

}
