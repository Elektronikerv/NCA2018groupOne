import {Injectable} from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {FulfillmentOrder} from "../model/fulfillmentOrder.model";
import {OrderStatus} from "../model/orderStatus.model";

const url = '/api/fulfillmentOrder';

@Injectable()
export class FulfillmentOrderService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<FulfillmentOrder>) {
  }

    getServicesForCourier(status : OrderStatus, courierId : number): Observable<FulfillmentOrder[]> {
    console.log('Service getService(type) with orderStatus '  + status.name);
    console.log('Service getService(type) for courier id '  + courierId);
    return this.tokenService.get(`${url}/status/${status.id}/courier/${courierId}`);
  }





}
