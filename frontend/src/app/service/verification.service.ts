import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

const url = "/api/verify";

@Injectable()
export class VerificationService {

  constructor(private http: HttpClient) { }

  verifyEmail(email: string, hash: string) {
    console.log("verify email to " + email);
    return this.http.get(`${url}?email=${email}&hash=${hash}`);
  }
}
