import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";
import {CourierService} from "../../service/сourier.service";
import {ORDER_STATUSES, OrderStatus} from "../../model/orderStatus.model";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {Advert} from "../../model/advert.model";
import {Router} from "@angular/router";
import {JwtHelper} from "angular2-jwt";
import { CourierPoint } from '../../model/courierPoint.model';
import {GoogleMapsComponent} from "../utils/google-maps/google-maps.component";

import {MapsAPILoader} from "@agm/core";
import { Coordinates } from '../../model/coordinates.model';

@Component({
  moduleId: module.id,
  selector: 'app-courier',
  templateUrl: 'courier.component.html',
  styleUrls: ['courier.component.css']
})
export class CourierComponent implements OnInit {
  courierId: number;

  map: GoogleMapsComponent;
  courierWay: CourierPoint[] = [];

  clicked: boolean = false;

  @ViewChild('plug')
  ref: ElementRef;

  private jwtHelper: JwtHelper = new JwtHelper();

  constructor(private courierService: CourierService,
              private router: Router,
              private authService: AuthService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone)  {
      this.map = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit() {
    let token = localStorage.getItem("currentUser");
    this.courierId = +this.jwtHelper.decodeToken(token).id;
    this.getCourierWay();
    this.map.setSearchElement(this.ref);
    this.map.ngOnInit();
  }

  orderReceived(point: CourierPoint): void {
    this.courierService.orderReceived(point)
      .subscribe(_ => this.getCourierWay());

  }

  cancelReceiving(point: CourierPoint): void {
    this.courierService.cancelReceiving(point)
      .subscribe(_ => this.getCourierWay());

  }

  cancelDelivering(point: CourierPoint): void {
    this.courierService.cancelDelivering(point)
      .subscribe(_ => this.getCourierWay());

  }

  orderDelivered(point: CourierPoint): void {
    this.courierService.orderDelivered(point)
      .subscribe(_ => this.getCourierWay());
  }

  getCourierWay() {
    this.courierService.getCourierWay(this.courierId)
      .subscribe((way : CourierPoint[]) => this.courierWay = way);
  }

  isGiveDisabled(point: CourierPoint): boolean {
    return (point.order.orderStatus == 'DELIVERING') ? false : true;
  }

  isTakeDisabled(point: CourierPoint): boolean {
    return !this.isGiveDisabled(point);
  }
}
