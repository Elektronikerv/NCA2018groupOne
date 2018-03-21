import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {OrderService} from '../../../service/order.service';
import {Order} from '../../../model/order.model';
import {OfficeService} from '../../../service/office.service';
import {Office} from '../../../model/office.model';
import {User} from '../../../model/user.model';
import {FLAT_PATTERN, FLOOR_PATTERN} from '../../../model/utils';
import {JwtHelper} from 'angular2-jwt';
import {GoogleMapsComponent} from '../../utils/google-maps/google-maps.component';
import {MapsAPILoader} from '@agm/core';
import {AuthService} from '../../../service/auth.service';

import {TransferService} from '../../../service/transfer.service';

@Component({
  moduleId: module.id,
  selector: 'app-c-c-edit-order-client',
  templateUrl: 'edit-c-c-order-client.component.html',
  styleUrls: ['edit-c-c-order-client.component.css']
})
export class EditCCOrderClientComponent implements OnInit {

  private jwtHelper: JwtHelper = new JwtHelper();
  orderForm: FormGroup;
  senderAddress: FormGroup;
  receiverAddress: FormGroup;
  isOfficeClientDelivery: boolean;


  currentUser: User;
  order: Order;
  orderId: number;
  offices: Office[];
  office: Office = <Office>{};

  mapFrom: GoogleMapsComponent;
  mapTo: GoogleMapsComponent;

  receiverAvailabilityFrom = '';
  receiverAvailabilityTo = '';
  receiverAvailabilityDate = '';

  @ViewChild('searchAddressFrom')
  public searchAddressFromRef: ElementRef;
  @ViewChild('searchAddressTo')
  public searchAddressToRef: ElementRef;

  constructor(private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService,
              private officeService: OfficeService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone,
              private transferService: TransferService) {
    this.mapFrom = new GoogleMapsComponent(mapsAPILoader, ngZone);
    this.mapTo = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    this.mapFrom.setSearchElement(this.searchAddressFromRef);
    this.mapFrom.ngOnInit();
    this.mapTo.setSearchElement(this.searchAddressToRef);
    this.mapTo.ngOnInit();
    this.authService.currentUser().subscribe((user: User) => {
      this.currentUser = user;
      const id = +this.activatedRouter.snapshot.paramMap.get('id');
      this.getOrder(id, user.id);
      //        this.orderId = this.transferService.getOrderId();
      //   console.log('input order: ' + this.orderId);
    });

    this.initCreateForm();
  }

  initCreateForm(): FormGroup {
    return this.orderForm
      = this.formBuilder.group({
      senderAddressForm: this.initSenderAddress(),
      receiverAddressForm: this.initReceiverAddress(),
      description: [''],
      receiverAvailabilityDate: ['', [Validators.required]],
      receiverAvailabilityFrom: ['', [Validators.required]],
      receiverAvailabilityTo: ['', [Validators.required]]
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

  initReceiverAddress(): FormGroup {
    return this.receiverAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [0, [Validators.required, Validators.pattern(FLOOR_PATTERN)]],
      flat: [0, [Validators.required, Validators.pattern(FLAT_PATTERN)]]
    });
  }


  getOrder(orderId: number, userId: number) {
    this.orderService.getOrderById(orderId, userId)
      .subscribe((order: Order) => {
        this.order = order;
        this.getOffices();
      });
  }


  createDraft(): void {
    this.order.user = this.currentUser;
    this.order.orderStatus = 'DRAFT';
    console.log('Create draft: ' + JSON.stringify(this.order));
    this.orderService.create(this.order).subscribe((order: Order) => {
      console.log('Created draft number ' + order.id + ' for user ' + this.currentUser.id);
      this.router.navigate(['orderHistory/' + this.currentUser.id]);
    });
  }


  confirmOrder() {
    // this.fullFillmentOrder.order.orderStatus = "CONFIRMED";
    this.order.receiverAvailabilityTimeFrom = this.receiverAvailabilityDate + ' ' + this.receiverAvailabilityFrom + ':00';
    this.order.receiverAvailabilityTimeTo = this.receiverAvailabilityDate + ' ' + this.receiverAvailabilityTo + ':00';

    // this.orderService.confirmFulfillmentOrder(this.order)
    //   .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  cancelOrder() {
    this.order.orderStatus = 'CANCELLED'; // Move this action to UI
    this.update();
  }

  update() {
    this.orderService.update(this.order).subscribe((order: Order) => {
      this.reRout(this.currentUser.id);
    })
  }

  getOffices(): void {
    this.officeService.getOffices().subscribe(offices => this.offices = offices);
  }

  validateField(field: string): boolean {
    return this.orderForm.get(field).valid || !this.orderForm.get(field).dirty;
  }

  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddress.get(field).valid || !this.senderAddress.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }

  updateStreetFrom() {
    this.order.senderAddress.street = this.mapFrom.street;
  }

  updateHouseFrom() {
    this.order.senderAddress.house = this.mapFrom.house;
  }

  updateStreetHouseFrom() {
    setTimeout(() => {
      this.order.senderAddress.street = this.mapFrom.street;
      this.order.senderAddress.house = this.mapFrom.house;
    }, 500);
  }

  mapFromReady($event, yourLocation) {
    this.mapFrom.mapReady($event, yourLocation);
    this.mapFrom.geocodeAddress(this.order.senderAddress.street, this.order.senderAddress.house);
  }

  updateStreetTo() {
    this.order.receiverAddress.street = this.mapTo.street;
  }

  updateHouseTo() {
    this.order.receiverAddress.house = this.mapTo.house;
  }

  updateStreetHouseTo() {
    setTimeout(() => {
      this.order.receiverAddress.street = this.mapTo.street;
      this.order.receiverAddress.house = this.mapTo.house;
    }, 500);
  }

  mapToReady($event, yourLocation) {
    this.mapTo.mapReady($event, yourLocation);
    this.mapTo.geocodeAddress(this.order.receiverAddress.street, this.order.receiverAddress.house);
  }

  reRout(currentUserId: number) {
    console.log(JSON.stringify(currentUserId));
    this.orderService.getOrdersByUserId(currentUserId).subscribe(() => this.router.navigate(['/orderHistory/']));
  }
}
