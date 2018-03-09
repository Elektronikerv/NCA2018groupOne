import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {FulfillmentOrder} from "../model/fulfillmentOrder.model";
import {OrderStatus} from "../model/orderStatus.model";

const url = '/api/courier';

@Injectable()
export class CourierService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<FulfillmentOrder>) {
  }

  getFulfillmentOrdersForCourier(courierId : number): Observable<FulfillmentOrder[]> {
  console.log('Service getFulfillmentOrdersForCourier(type) with orderStatus' +
    ' EXECUTING AND DELIVERING');
  console.log('Service getFulfillmentOrdersForCourier for courier id '  + courierId);
  return this.tokenService.get(`${url}/${courierId}`);
  }

  orderReceived(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service orderReceived() for fulfillment with id ' + fulfillment.id);
    return this.tokenService.put(`${url}/orderReceived`, fulfillment);
  }

  isntReceived(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service isntReceived() for fulfillment with id ' + fulfillment.id);
    return this.tokenService.put(`${url}/isntReceived`, fulfillment);
  }

  cancelExecution(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service cancelExecution() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/cancelExecution`, fulfillment);
  }

  cancelDelivering(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service cancelDelivering() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/cancelDelivering`, fulfillment);
  }

  orderDelivered(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service orderDelivered() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/orderDelivered`, fulfillment);
  }


  isntDelivered(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service isNotDelivered() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/isntDelivered`, fulfillment);
  }
}
