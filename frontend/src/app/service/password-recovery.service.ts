import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

const url = "/api/recovery";

@Injectable()
export class PasswordRecoveryService {

  constructor(private http: HttpClient) { }

  recoverPassword(email: string) {
    console.log("recover password  to " + email);
    return this.http.get(`${url}?email=${email}`);
  }
}
