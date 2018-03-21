import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {FulfillmentOrder} from "../model/fulfillmentOrder.model";
import {OrderStatus} from "../model/orderStatus.model";
import { CourierPoint } from '../model/courierPoint.model';

const url = '/api/courier';

@Injectable()
export class CourierService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<CourierPoint>) {
  }

  orderReceived(point: CourierPoint): Observable<CourierPoint>{
    console.log('Service orderReceived() for order with id ' + point.order.id);
    return this.tokenService.put(`${url}/orderReceived`, point);
  }

  cancelReceiving(point: CourierPoint): Observable<CourierPoint>{
    console.log('Service isntReceived() for order with id ' + point.order.id);
    return this.tokenService.put(`${url}/cancelReceiving`, point);
  }

  cancelDelivering(point: CourierPoint): Observable<CourierPoint>{
    console.log('Service cancelDelivering() order with id  '+ point.order.id);
    return this.tokenService.put(`${url}/cancelDelivering`, point);
  }

  orderDelivered(point: CourierPoint): Observable<CourierPoint>{
    console.log('Service orderDelivered() order with id  '+ point.order.id);
    return this.tokenService.put(`${url}/orderDelivered`, point);
  }

  getCourierWay(courierId : number): Observable<CourierPoint[]> {
    console.log('Get courier way for courier with id ' + courierId);
    return this.tokenService.get(`${url}/way/${courierId}`);
  }

}
