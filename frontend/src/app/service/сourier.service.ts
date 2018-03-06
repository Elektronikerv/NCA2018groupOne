import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {FulfillmentOrder} from "../model/fulfillmentOrder.model";
import {OrderStatus} from "../model/orderStatus.model";

const url = '/api/fulfillment';

@Injectable()
export class CourierService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<FulfillmentOrder>) {
  }

  getFulfillmentOrdersForCourier(status : OrderStatus, courierId : number): Observable<FulfillmentOrder[]> {
  console.log('Service getService(type) with orderStatus '  + status.name);
  console.log('Service getService(type) for courier id '  + courierId);
  return this.tokenService.get(`${url}/status/${status.id}/courier/${courierId}`);
  }

  cancelAssignment(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service cancelAssignment() for fulfillment with id ' + fulfillment.id);
    return this.tokenService.put(`${url}/cancelAssignment`, fulfillment);
  }

  acceptOrderForDelivering(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service acceptOrderForDelivering() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/acceptForFulfillment`, fulfillment);
  }

  cancelDelivery(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service cancelDelivery() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/confirmExecution`, fulfillment);
  }

  confirmExecution(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service confirmExecution() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/confirmExecution`, fulfillment);
  }

  isNotDelivered(fulfillment: FulfillmentOrder): Observable<FulfillmentOrder>{
    console.log('Service isNotDelivered() fulfillment with id  '+ fulfillment.id);
    return this.tokenService.put(`${url}/isNotDelivered`, fulfillment);
  }
}
