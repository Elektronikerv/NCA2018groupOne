import {Component, OnInit, Input, NgZone, ElementRef, ViewChild} from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators, FormControl } from "@angular/forms";
import { OrderService } from "../../../service/order.service";
import { Order } from "../../../model/order.model";
import { OfficeService } from "../../../service/office.service";
import { Office } from '../../../model/office.model';
import { OrderStatus } from '../../../model/orderStatus.model';
import {CustomValidators} from "ng2-validation";
import { ORDER_STATUSES } from '../../../model/orderStatus.model';
import { FulfillmentOrder } from '../../../model/fulfillmentOrder.model';
import { User } from '../../../model/user.model';
import {Address} from "../../../model/address.model";
import {FLAT_PATTERN, FLOOR_PATTERN} from "../../../model/utils";
import {JwtHelper} from "angular2-jwt";
import {GoogleMapsComponent} from "../../utils/google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {AuthService} from "../../../service/auth.service";

@Component({
  moduleId: module.id,
  selector: 'app-edit-order-client',
  templateUrl: 'edit-order-client.component.html',
  styleUrls: ['edit-order-client.component.css']
})
export class EditOrderClientComponent implements OnInit {

  private jwtHelper: JwtHelper = new JwtHelper();
  currentUserId : number;

  office1 : Office;

  orderForm: FormGroup;
  senderAddressForm: FormGroup;
  receiverAddressForm: FormGroup;
  officeForm : FormGroup;
  isOfficeClientDelivery : boolean = false;

  currentUser: User;
  order: Order;
  offices: Office[];

  mapTo: GoogleMapsComponent;
  mapFrom: GoogleMapsComponent;

  receiverAvailabilityFrom : string = '';
  receiverAvailabilityTo : string = '';
  receiverAvailabilityDate : string = '';


  @ViewChild('searchAddressTo')
  public searchAddressToRef: ElementRef;
  @ViewChild('searchAddressFrom')
  public searchAddressFromRef: ElementRef;

  constructor(private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService,
              private officeService: OfficeService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone) {
    this.mapTo = new GoogleMapsComponent(mapsAPILoader,ngZone);
    this.mapFrom = new GoogleMapsComponent(mapsAPILoader,ngZone);
  }

  ngOnInit(): void {
    this.mapTo.setSearchElement(this.searchAddressToRef);
    this.mapTo.ngOnInit();
    this.mapFrom.setSearchElement(this.searchAddressFromRef);
    this.mapFrom.ngOnInit();
    this.getOffices();

    this.authService.currentUser().subscribe((user: User) => this.currentUser = user);

    this.initCreateForm();
    // order
  }

  initCreateForm(): FormGroup{
    return this.orderForm
      = this.formBuilder.group({
      officeForm:  this.initEmptyOfficeForm(),
      senderAddressForm: this.initSenderAddressForm(),
      receiverAddressForm: this.initReceiverAddressForm(),
      description: [''],
      receiverAvailabilityDate: ['', [Validators.required]],
      receiverAvailabilityFrom:['', [Validators.required]],
      receiverAvailabilityTo:['', [Validators.required]]
    });
  }

  initSenderAddressForm()  : FormGroup {
    return this.senderAddressForm = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [ [0,Validators.required,Validators.pattern(FLOOR_PATTERN)]],
      flat:  [ [0,Validators.required,Validators.pattern(FLAT_PATTERN)]]
    });
  }
  initOfficeForm()  : FormGroup {
    return this.officeForm = this.formBuilder.group({
      office: ['', Validators.required]
    });
  }
  initEmptyOfficeForm()  : FormGroup {
    return this.officeForm = this.formBuilder.group({
      office: new FormControl()
    });
  }
  initEmptySenderAddressForm()  : FormGroup {
    return this.senderAddressForm = this.formBuilder.group({
      street: new FormControl(),
      house: new FormControl(),
      floor: new FormControl(),
      flat: new FormControl()
    });
  }

  refreshOfficeForm(){
    this.isOfficeClientDelivery=!this.isOfficeClientDelivery;
    this.orderForm.removeControl('senderAddress');
    this.orderForm.addControl('officeForm', this.initOfficeForm());

  }

  refreshSenderForm(){
    this.isOfficeClientDelivery=!this.isOfficeClientDelivery;
    this.orderForm.removeControl('officeForm');
    this.orderForm.addControl('senderAddress', this.initSenderAddressForm());

    // this.createOrderForm.setControl('senderAddress',);
    // this.createOrderForm.setControl('officeForm' , this.initEmptyOfficeForm());
  }

  initReceiverAddressForm() : FormGroup {
    return this.receiverAddressForm = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }


  getOrder() {
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    this.orderService.getOrderById(id)
      .subscribe((order: Order) => {this.order = order;
        this.office1 = order.office;
        });


  }

  getOffices() {
    this.officeService.getOffices()
      .subscribe(offices => this.offices = offices);

  }


  confirmOrder() {
    // this.fullFillmentOrder.order.orderStatus = "CONFIRMED";
    this.order.receiverAvailabilityTimeFrom = new Date(this.receiverAvailabilityDate + this.receiverAvailabilityFrom);
    this.order.receiverAvailabilityTimeTo = new Date(this.receiverAvailabilityDate + this.receiverAvailabilityTo);

    // this.orderService.confirmFulfillmentOrder(this.order)
    //   .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  cancelOrder() {
    this.order.orderStatus = "CANCELLED"; // Move this action to UI
    this.update();
  }

  update() {
    this.orderService.update(this.order)
      .subscribe(_ => this.router.navigate(['orderHistory/'+ this.currentUserId]));
  }

  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddressForm.get(field).valid || !this.senderAddressForm.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddressForm.get(field).valid || !this.receiverAddressForm.get(field).dirty;
  }

}
