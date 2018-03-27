import {Injectable} from '@angular/core';
import {Order} from '../model/order.model';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token.service';
import {FulfillmentOrder} from '../model/fulfillmentOrder.model';
import {User} from '../model/user.model';
import {OrderHistory} from '../model/orderHistory.model';

const url = '/api/orders';

@Injectable()
export class OrderService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<Order>,
              private fulfilmentTokenService: TokenService<FulfillmentOrder>) {
  }

  // getOrderById(id: number): Observable<Order> {
  //   return this.tokenService.get(`${url}/${id}`);
  // }e

  getOrderById(orderId: number, userId: number): Observable<Order> {
    const params: Array<[string, number]> = [['orderId', orderId], ['userId', userId]];
    return this.tokenService.getWithParams(`${url}/orderHistory/infoCurrentOrder`, params);
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
    return this.fulfilmentTokenService.put(`${url}/fo/update`, fulfillmentOrder);
  }

  cancelFulfillmentOrder(fulfillmentOrder: FulfillmentOrder): Observable<FulfillmentOrder> {
    return this.fulfilmentTokenService.put(`${url}/fo/cancel`, fulfillmentOrder);
  }

  cancelAttempt (fulfillmentOrder: FulfillmentOrder): Observable<FulfillmentOrder> {
    return this.fulfilmentTokenService.put(`${url}/fo/cancelAttempt`, fulfillmentOrder);
  }


  getOrders(): Observable<Order[]> {
    return this.tokenService.get(url);
  }

  getOrdersByUserId(userId: number): Observable<OrderHistory[]> {
    const params: Array<[string, number]> = [['userId', userId]];
    return this.tokenService.getWithParams(`${url}/orderHistory/`, params);
  }

  getOrdersByUserIdSorted(userId: number, sortedField: string, asc: boolean): Observable<OrderHistory[]> {
    const params: Array<[string, any]> = [['userId', userId], ['sortedField', sortedField], ['asc', asc]];
    return this.tokenService.getWithParams(`${url}/orderHistory/sort`, params);
  }

  update(order: Order): Observable<Order> {
    return this.tokenService.put(`${url}/${order.id}`, order);
  }


  createOrder(order: Order): Observable<Order> {
    // console.log('Order service: create order');
    return this.tokenService.post(url, order);
  }

  createOrderFromDraft(order: Order): Observable<Order> {
    // console.log('Order service: create order');
    return this.tokenService.post(`${url}/confirmDraft`, order);
  }


  createDraft(order: Order): Observable<Order> {
    // console.log('Order service: create order');
    return this.tokenService.post(`${url}/createDraft`, order);
  }

  saveFeedback(order : Order):Observable<Order>{
    return this.tokenService.post(`${url}/saveFeedback`, order);
  }

  cancelOrder(order: Order): Observable<Order> {
    // console.log('Order service: create order');
    return this.tokenService.post(`${url}/cancelOrder`, order);
  }

  deleteDraft(order : Order): Observable<Order>{
    return this.tokenService.post(`${url}/deleteDraft`,order);
  }

}
