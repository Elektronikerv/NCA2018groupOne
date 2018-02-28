import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {OrderHistory} from "../model/orderHistory.model";
import {Observable} from "rxjs/Observable";

const url = '/api/orderHistory'

@Injectable()
export class OrderHistoryService {

  constructor(private http: HttpClient, private tokenService: TokenService<OrderHistory>) {
  }


  getOrdersByUserId(id: number): Observable<OrderHistory[]> {
    console.log("getOrdersByUserId");
    return this.tokenService.get(`${url}/${id}`);
  }


}
