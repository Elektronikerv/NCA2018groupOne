import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {OrderService} from "../../service/order.service";
import {Order} from "../../model/order.model";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {Address} from '../../model/address.model';
import {CustomValidators} from "ng2-validation";
import {Office} from "../../model/office.model";
import {OfficeService} from "../../service/office.service";
import {GoogleMapsComponent} from "../google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";

@Component({
  moduleId: module.id,
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent extends GoogleMapsComponent implements OnInit {
  createOrderForm: FormGroup;
  senderAddress: FormGroup;
  receiverAddress: FormGroup;
  currentUser: User;
  order: Order;
  offices: Office[];
  toLatitude: number;
  toLongitude: number;
  toStreet: string;
  toHouse: string;
  toZoom: number;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService,
              private officeService: OfficeService,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
    super(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    super.ngOnInit()
    this.getOffices();
    this.order = <Order>{};
    this.order.senderAddress = <Address>{};
    this.order.receiverAddress = <Address>{};
    console.log('Init create-order');
    this.authService.currentUser().subscribe((user: User) => this.currentUser = user);
    this.createOrderForm = this.formBuilder.group({
      senderAddress: this.initSenderAddress(),
      receiverAddress: this.initReceiverAddress(),
      office: new FormControl(),
      description: ['']
    });
    this.toLatitude=50.449392;
    this.toLongitude = 30.523408;
    this.toZoom = 16;
  }

  initSenderAddress() {
    return this.senderAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(0), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]]
    });
  }

  initReceiverAddress() {
    return this.receiverAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(-20), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(1000)]]
    });
  }

  createOrder(order: Order): void {
    console.log("Create order");
    order.user = this.currentUser;
    order.orderStatus = "OPEN";
    this.orderService.create(order).subscribe((order: Order) => {
      console.log("Created OPEN order number " + order.id + " for user " + this.currentUser.id);
      this.router.navigate(['orderHistory/' + this.currentUser.id]);
    })
  }

  createDraft(): void {
    this.order.user = this.currentUser;
    this.order.orderStatus = "DRAFT";
    console.log('Create draft: ' + JSON.stringify(this.order));
    this.orderService.create(this.order).subscribe((order: Order) => {
      console.log("Created draft number " + order.id + " for user " + this.currentUser.id);
      this.router.navigate(['orderHistory/' + this.currentUser.id]);
    })
  }
  placeMarkerTo($event) {
    this.toLatitude= $event.coords.lat;
    this.toLongitude = $event.coords.lng;
    this.geocode(new google.maps.LatLng(this.toLatitude, this.toLongitude));
  }

  geocode(latLng) {
    let geocoder = new google.maps.Geocoder();
    geocoder.geocode({'location': latLng}, (results, status) => {
      if (status == google.maps.GeocoderStatus.OK) {
        if (results[1]) {
          this.toStreet = results[0].formatted_address.split(',')[0].trim();
          this.toHouse = results[0].formatted_address.split(',')[1].trim();
        } else {
          alert('Location not found');
        }
      }
      else {
        alert('Geocoder failed due to: ' + status);
      }
    });
  }

  getOffices(): void {
    this.officeService.getOffices().subscribe(offices => this.offices = offices);
  }

  validateField(field: string): boolean {
    return this.createOrderForm.get(field).valid || !this.createOrderForm.get(field).dirty;
  }

  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddress.get(field).valid || !this.senderAddress.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }
}
