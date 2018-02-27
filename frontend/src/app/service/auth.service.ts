import {Injectable, Input, Output} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {UserAuthParam} from "../model/userAuthParam.model";
import 'rxjs/add/operator/map'
import {JwtHelper} from "angular2-jwt";
import {UserService} from "./user.service";
import {User} from "../model/user.model";
import {Role} from "../model/role.model";

const url = '/api/auth';

@Injectable()
export class AuthService {
  private JwtHelper: JwtHelper = new JwtHelper();


  constructor(private http: HttpClient, private userService: UserService) {
  }

  login(userAuthParam: UserAuthParam): Observable<Response> {
    return this.http.post<any>(url, {email: userAuthParam.email, password: userAuthParam.password})
      .map(userParam => {
        console.log('login(), test');
        if (userParam && userParam.token) {
          console.log('login(),  userParam.token: ' +  userParam.token);
          console.log('login(),  JSON.parse(JSON.stringify(userParam)).token: ' +  JSON.parse(JSON.stringify(userParam)).token);
        //   console.log('login(), JSON.stringify(userParam): ' + JSON.stringify(userParam));
        //   console.log('userParam.json(): ' + userParam);

        //   console.log('userParam.json().token: ' + userParam.json().token);
        //   console.log('JSON.stringify(userParam): ' + JSON.stringify(userParam));
          localStorage.setItem('currentUser', userParam.token);
        }
        return userParam;
      });
  }

  currentUser(): Observable<User> {
    let token = localStorage.getItem("currentUser");
    let userId = +this.JwtHelper.decodeToken(token).id;
    // console.log('id: ' + userId);

    return this.userService.getUser(userId).map((user: User) => {//map
      console.log('user1: ' + JSON.stringify(user));
      return user;
    })
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  checkSignIn(): boolean {
    // console.log("checkSignIn(), currentUser: " + localStorage.getItem('currentUser'));
    return !!localStorage.getItem('currentUser'); // !! not null === return true
  }

  user: User;

  // getUserRoles(): Role[]{
  //   return this.currentUser().subscribe((user: User) => {
  //     console.log('user2: ' + JSON.stringify(user));
  //     this.user = user;
  //     return user.roles;
  //   });
    // console.log('getUserRoles: ' + JSON.stringify(this.user));
    // return this.user.roles;
  // }

}
