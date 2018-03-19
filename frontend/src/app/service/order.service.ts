import {Injectable} from '@angular/core';
import {Order} from "../model/order.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {FulfillmentOrder} from '../model/fulfillmentOrder.model';
import {User} from '../model/user.model';

const url = '/api/orders';

@Injectable()
export class OrderService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<Order>,
              private fulfilmentTokenService: TokenService<FulfillmentOrder>) {
  }

  getOrderById(id: number): Observable<Order> {
    return this.tokenService.get(`${url}/${id}`);
  }

  getFulfillmentOrderById(id: number): Observable<FulfillmentOrder> {
    return this.tokenService.get(`${url}/fo/${id}`);
  }

  getFulfillmentOrders(ccagentId: number): Observable<FulfillmentOrder[]> {
    return this.tokenService.get(`${url}/ccagent/${ccagentId}/fo`);
  }

  getAllCouriers(): Observable<User[]> {
    return this.tokenService.get(`${url}/couriers`);
  }

  startProcessing(ccagentId: number, fulfillmentOrder: FulfillmentOrder): Observable<FulfillmentOrder> {
    return this.fulfilmentTokenService.post(`${url}/fo/${ccagentId}`, fulfillmentOrder);
  }

  confirmFulfillmentOrder(fulfillmentOrder: FulfillmentOrder): Observable<FulfillmentOrder> {
    return this.fulfilmentTokenService.put(`${url}/fo/confirmation`, fulfillmentOrder);
  }

  updateFulfillmentOrder(fulfillmentOrder: FulfillmentOrder): Observable<FulfillmentOrder> {
    return this.fulfilmentTokenService.put(`${url}/fo/${fulfillmentOrder.id}`, fulfillmentOrder);
  }

  getOrders(): Observable<Order[]> {
    return this.tokenService.get(url);
  }

  update(order: Order): Observable<Order> {
    return this.tokenService.put(`${url}/${order.id}`, order);
  }


  create(order: Order): Observable<Order> {
    console.log("Order service: create order");
    return this.tokenService.post(url, order);
  }

}
