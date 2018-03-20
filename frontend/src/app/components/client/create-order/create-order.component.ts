import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {OrderService} from "../../../service/order.service";
import {Order} from "../../../model/order.model";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {Office} from "../../../model/office.model";
import {OfficeService} from "../../../service/office.service";
import {GoogleMapsComponent} from "../../utils/google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {FLAT_PATTERN, FLOOR_PATTERN, OFFICE_ID_PATTERN} from "../../../model/utils";

@Component({
  moduleId: module.id,
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {
  createOrderForm: FormGroup;
  senderAddress: FormGroup;
  isOfficeClientDelivery: boolean = false;

  currentUser: User;
  order: Order;
  offices: Office[];

  mapTo: GoogleMapsComponent;
  mapFrom: GoogleMapsComponent;

  receiverAvailabilityFrom: string = '';
  receiverAvailabilityTo: string = '';
  receiverAvailabilityDate: string = '';


  @ViewChild('searchAddressTo')
  public searchAddressToRef: ElementRef;
  @ViewChild('searchAddressFrom')
  public searchAddressFromRef: ElementRef;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService,
              private officeService: OfficeService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone) {
    this.mapTo = new GoogleMapsComponent(mapsAPILoader, ngZone);
    this.mapFrom = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    this.mapTo.setSearchElement(this.searchAddressToRef);
    this.mapTo.ngOnInit();
    this.mapFrom.setSearchElement(this.searchAddressFromRef);
    this.mapFrom.ngOnInit();
    this.getOffices();
    this.order = <Order>{};
    this.authService.currentUser().subscribe((user: User) => this.currentUser = user);

    this.initCreateForm();

  }

  initCreateForm(): FormGroup {
    return this.createOrderForm
      = this.formBuilder.group({
      office: this.initEmptyOfficeForm(),
      senderAddress: this.initSenderAddress(),
      receiverAddress: this.initReceiverAddress(),
      description: [''],
      receiverAvailabilityDate: ['', [Validators.required]],
      receiverAvailabilityFrom: ['', [Validators.required]],
      receiverAvailabilityTo: ['', [Validators.required]],
      receiverAvailabilityTimeFrom: new FormControl(),
      receiverAvailabilityTimeTo: new FormControl()
    });
  }

  initOfficeForm(): FormControl {
    return new FormControl(null, [Validators.required]);
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
    return this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [0, [Validators.required, Validators.pattern(FLOOR_PATTERN)]],
      flat: [0, [Validators.required, Validators.pattern(FLAT_PATTERN)]]
    });
  }

  refreshOfficeForm() {
    this.isOfficeClientDelivery = true;
    this.createOrderForm.removeControl('senderAddress');
    this.createOrderForm.setControl('office', this.initOfficeForm());


  }

  refreshSenderForm() {
    this.isOfficeClientDelivery = false;
    this.createOrderForm.removeControl('officeForm');
    this.createOrderForm.setControl('senderAddress', this.initSenderAddress());

  }

  initEmptyOfficeForm(): FormControl {
    return new FormControl();
  }

  initEmptySenderAddress(): FormGroup {
    return this.senderAddress = this.formBuilder.group({
      street: new FormControl(''),
      house: new FormControl(''),
      floor: new FormControl(0),
      flat: new FormControl(0)
    });
  }

  createOrder(order: any): void {
    order.user = this.currentUser;
    order.orderStatus = "OPEN";

    order.receiverAvailabilityTimeFrom = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityFrom + ':00';
    order.receiverAvailabilityTimeTo = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityTo + ':00';

    console.log('Create draft: ' + JSON.stringify(order));
    this.orderService.create(order).subscribe((order1: Order) => {
      console.log("Created OPEN order number " + order1.id + " for user " + this.currentUser.id);
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
    return this.createOrderForm.get('receiverAddress').valid || !this.createOrderForm.get('receiverAddress').get(field).dirty;
  }

}
