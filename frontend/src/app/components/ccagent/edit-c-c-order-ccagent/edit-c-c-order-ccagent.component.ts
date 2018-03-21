import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {OrderService} from "../../../service/order.service";
import {Order} from "../../../model/order.model";
import {CustomValidators} from "ng2-validation";
import {FulfillmentOrder} from '../../../model/fulfillmentOrder.model';
import {User} from '../../../model/user.model';
import {Address} from "../../../model/address.model";
import {FLAT_PATTERN, FLOOR_PATTERN} from "../../../model/utils";
import {GoogleMapsComponent} from "../../utils/google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";

@Component({
  selector: 'app-edit-c-c-order-ccagent',
  templateUrl: './edit-c-c-order-ccagent.component.html',
  styleUrls: ['./edit-c-c-order-ccagent.component.css']
})
export class EditCCOrderCcagentComponent implements OnInit {

  fulfillmentOrder: FulfillmentOrder = <FulfillmentOrder>{};
  orderForm: FormGroup;
  senderAddress: FormGroup;
  receiverAddress: FormGroup;
  officeId: number;

  mapSender: GoogleMapsComponent;
  mapReceiver: GoogleMapsComponent;

  @ViewChild('searchSenderAddress')
  public searchSenderAddressRef: ElementRef;
  @ViewChild('searchReceiverAddress')
  public searchReceiverAddressToRef: ElementRef;

  constructor(private orderService: OrderService,
              private activatedRouter: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone) {
    this.fulfillmentOrder.order = <Order>{};
    this.fulfillmentOrder.order.user = <User>{};
    this.fulfillmentOrder.order.senderAddress = <Address>{};
    this.fulfillmentOrder.order.receiverAddress = <Address>{};

    this.mapSender = new GoogleMapsComponent(mapsAPILoader, ngZone);
    this.mapReceiver = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    this.mapSender.setSearchElement(this.searchSenderAddressRef);
    this.mapSender.ngOnInit();
    this.mapReceiver.setSearchElement(this.searchReceiverAddressToRef);
    this.mapReceiver.ngOnInit();
    this.getFulfillmentOrder();
  }

  initForm(fulfillment: FulfillmentOrder) {
    this.orderForm = this.formBuilder.group({
        senderAddress: this.initSenderAddress(),
        receiverAddress: this.initReceiverAddress(),
        description: new FormControl(CustomValidators.required),
        receiverAvailabilityDate: ['', [Validators.required]],
        receiverAvailabilityFrom: ['', [Validators.required]],
        receiverAvailabilityTo: ['', [Validators.required]],
        receiverAvailabilityTimeFrom: new FormControl(),
        receiverAvailabilityTimeTo: new FormControl()
      }
    );
  }

  initReceiverAddress(): FormGroup {
    return this.receiverAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [0, [Validators.required, Validators.pattern(FLOOR_PATTERN)]],
      flat: [0, [Validators.required, Validators.pattern(FLAT_PATTERN)]]
    });
  }

  initSenderAddress(): FormGroup {
    return this.senderAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [0, [Validators.required, Validators.pattern(FLOOR_PATTERN)]],
      flat: [0, [Validators.required, Validators.pattern(FLAT_PATTERN)]]
    });
  }


  getFulfillmentOrder() {
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    this.orderService.getFulfillmentOrderById(id)
      .subscribe((order: FulfillmentOrder) => {
        this.fulfillmentOrder = order;
        this.fulfillmentOrder.order.receiverAvailabilityDate = this.fulfillmentOrder.order.receiverAvailabilityTimeTo.toString().substring(0, 10);
        this.fulfillmentOrder.order.receiverAvailabilityFrom = this.fulfillmentOrder.order.receiverAvailabilityTimeFrom.toString().substring(11, 16);
        this.fulfillmentOrder.order.receiverAvailabilityTo = this.fulfillmentOrder.order.receiverAvailabilityTimeTo.toString().substring(11, 16);
        this.initForm(order);
      });
  }

  confirmOrder(order: Order) {
    this.updateAvailabilityTime();
    this.orderService.confirmFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  cancelOrder() {
    this.orderService.cancelFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']))
  }

  save(order: Order) {
    this.updateAvailabilityTime();
    this.update();
  }

  update() {
    this.orderService.updateFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  private updateAvailabilityTime() {
    console.log('From ' + this.fulfillmentOrder.order.receiverAvailabilityTimeFrom);
    console.log('TO ' + this.fulfillmentOrder.order.receiverAvailabilityTimeFrom);
    this.fulfillmentOrder.order.receiverAvailabilityTimeFrom = this.fulfillmentOrder.order.receiverAvailabilityDate + ' ' + this.fulfillmentOrder.order.receiverAvailabilityFrom + ':00';
    this.fulfillmentOrder.order.receiverAvailabilityTimeTo = this.fulfillmentOrder.order.receiverAvailabilityDate + ' ' + this.fulfillmentOrder.order.receiverAvailabilityTo + ':00';
    console.log('From ' + this.fulfillmentOrder.order.receiverAvailabilityTimeFrom);
    console.log('TO ' + this.fulfillmentOrder.order.receiverAvailabilityTimeFrom);
  }


  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddress.get(field).valid || !this.senderAddress.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }

  updateSenderStreet() {
    this.fulfillmentOrder.order.senderAddress.street = this.mapSender.street;
  }

  updateSenderHouse() {
    this.fulfillmentOrder.order.senderAddress.house = this.mapSender.house;
  }

  updateSenderStreetHouse() {
    setTimeout(() => {
      this.fulfillmentOrder.order.senderAddress.street = this.mapSender.street;
      this.fulfillmentOrder.order.senderAddress.house = this.mapSender.house;
    }, 500);
  }

  mapSenderReady($event, yourLocation) {
    this.mapSender.mapReady($event, yourLocation);
    this.mapSender.geocodeAddress(this.fulfillmentOrder.order.senderAddress.street, this.fulfillmentOrder.order.senderAddress.house);
  }

  updateReceiverStreet() {
    this.fulfillmentOrder.order.receiverAddress.street = this.mapReceiver.street;
  }

  updateReceiverHouse() {
    this.fulfillmentOrder.order.receiverAddress.house = this.mapReceiver.house;
  }

  updateReceiverStreetHouse() {
    setTimeout(() => {
      this.fulfillmentOrder.order.receiverAddress.street = this.mapReceiver.street;
      this.fulfillmentOrder.order.receiverAddress.house = this.mapReceiver.house;
    }, 500);
  }

  mapReceiverReady($event, yourLocation) {
    this.mapReceiver.mapReady($event, yourLocation);
    this.mapReceiver.geocodeAddress(this.fulfillmentOrder.order.receiverAddress.street, this.fulfillmentOrder.order.receiverAddress.house);
  }

}
