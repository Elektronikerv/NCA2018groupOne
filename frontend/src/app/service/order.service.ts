import { Injectable } from '@angular/core';
import {Order} from "../model/order.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import { FulfillmentOrder } from '../model/fulfillmentOrder.model';
import { User } from '../model/user.model';

const url = '/api/orders';

@Injectable()
export class OrderService {

  constructor(private http: HttpClient,
    private tokenService: TokenService<Order>,
    private fulfilmentTokenService : TokenService<FulfillmentOrder>) { }

  getOrderById(id: number): Observable<Order> {
    return this.tokenService.get(`${url}/${id}`);
  }

  getFulfillmentOrderById(id: number): Observable<FulfillmentOrder> {
    return this.tokenService.get(`${url}/fo/${id}`);
  }

  getAllCouriers(): Observable<User[]> {
    return this.tokenService.get(`${url}/couriers`);
  }

  createFulfillmentOrder(ccagentId: number, orderId: number): Observable<number> {
    return this.tokenService.get(`${url}/fo/${ccagentId}/${orderId}`);
  }

  updateFullfimentOrder(fulfillmentOrder : FulfillmentOrder): Observable<FulfillmentOrder> {
    return this.fulfilmentTokenService.put(`${url}/fo/${fulfillmentOrder.id}`, fulfillmentOrder);
  }

  getOrders(): Observable<Order[]> {
    return this.tokenService.get(url);
  }

  update(order: Order): Observable<Order> {
    return this.tokenService.put(`${url}/${order.id}`, order);
  }



}
