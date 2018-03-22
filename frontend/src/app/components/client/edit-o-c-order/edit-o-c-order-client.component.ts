import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {OrderService} from '../../../service/order.service';
import {Order} from '../../../model/order.model';
import {OfficeService} from '../../../service/office.service';
import {Office} from '../../../model/office.model';
import {User} from '../../../model/user.model';
import {Address} from '../../../model/address.model';
import {FLAT_PATTERN, FLOOR_PATTERN} from '../../../model/utils';
import {JwtHelper} from 'angular2-jwt';
import {GoogleMapsComponent} from '../../utils/google-maps/google-maps.component';
import {MapsAPILoader} from '@agm/core';
import {AuthService} from '../../../service/auth.service';
import {DateValidatorService} from "../../../service/date-validator.service";

@Component({
  moduleId: module.id,
  selector: 'app-o-c-edit-order-client',
  templateUrl: 'edit-o-c-order-client.component.html',
  styleUrls: ['edit-o-c-order-client.component.css']
})
export class EditOCOrderClientComponent implements OnInit {

  private jwtHelper: JwtHelper = new JwtHelper();
  currentUserId: number;

  orderForm: FormGroup;
  receiverAddress: FormGroup;
  isOfficeClientDelivery: boolean;

  currentUser: User;
  order: Order = <Order>{};
  offices: Office[];

  mapReceiver: GoogleMapsComponent;

  receiverAvailabilityFrom = '';
  receiverAvailabilityTo = '';
  receiverAvailabilityDate = '';


  @ViewChild('searchReceiverAddress')
  public searchReceiverAddressRef: ElementRef;


  constructor(private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService,
              private officeService: OfficeService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone,
              private dateValidatorService: DateValidatorService) {
    this.mapReceiver = new GoogleMapsComponent(mapsAPILoader, ngZone);
    this.order.user = <User>{};
    this.order.office = <Office>{};
    this.order.receiverAddress = <Address>{};
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.mapReceiver.setSearchElement(this.searchReceiverAddressRef);
    }, 700);
    this.mapReceiver.ngOnInit();
    this.authService.currentUser().subscribe((user: User) => {
      this.currentUser = user;
      this.currentUserId = user.id;
      const id = +this.activatedRouter.snapshot.paramMap.get('id');
      this.getOrder(id, this.currentUserId);

    });
    this.getOffices();

    this.initCreateForm();
  }

  initCreateForm(): FormGroup {
    return this.orderForm
      = this.formBuilder.group({
      office: this.initOfficeForm(),
      receiverAddress: this.initReceiverAddress(),
      description: [''],
      receiverAvailabilityDate: [Validators.required],
      receiverAvailabilityFrom: [Validators.required],
      receiverAvailabilityTo: [Validators.required],
      receiverAvailabilityTimeFrom: new FormControl(),
      receiverAvailabilityTimeTo: new FormControl()
    } , {
      validator: [this.dateValidatorService.currentDayValidator('receiverAvailabilityDate'),
        this.dateValidatorService.timeFromValidator('receiverAvailabilityDate', 'receiverAvailabilityFrom'),
        this.dateValidatorService.timeRangeValidator('receiverAvailabilityFrom','receiverAvailabilityTo')]
    });

  }


  initOfficeForm(): FormControl {
    return new FormControl(null, [Validators.required]);
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
      });
  }

  createDraft(): void {
    this.order.user = this.currentUser;
    this.order.orderStatus = 'DRAFT';
    console.log('Create draft: ' + JSON.stringify(this.order));
    this.orderService.create(this.order).subscribe((order: Order) => {
      console.log('Created draft number ' + order.id + " for user " + this.currentUser.id);
      this.reRout(this.currentUserId);
    })
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
    this.orderService.update(this.order)
      .subscribe(() => this.reRout(this.currentUserId));
  }

  reRout(currentUserId: number){
    console.log('currentUserId: ' + JSON.stringify(currentUserId));
    this.orderService.getOrdersByUserId(currentUserId).subscribe(()=>{
      this.router.navigate(['/orderHistory/'])
    });
  }

  getOffices(): void {
    this.officeService.getOffices().subscribe(offices => this.offices = offices);
  }

  validateField(field: string): boolean {
    return this.orderForm.get(field).valid || !this.orderForm.get(field).dirty;
  }


  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }

  mapReceiverReady($event, yourLocation) {
    this.mapReceiver.mapReady($event, yourLocation);
    setTimeout(() => {
      this.mapReceiver.geocodeAddress(this.order.receiverAddress.street, this.order.receiverAddress.house);
    }, 700);
  }

  updateStreetReceiver() {
    this.order.receiverAddress.street = this.mapReceiver.street;
  }

  updateHouseReceiver() {
    this.order.receiverAddress.house = this.mapReceiver.house;
  }

  updateStreetHouseReceiver() {
    setTimeout(() => {
      this.order.receiverAddress.house = this.mapReceiver.house;
      this.order.receiverAddress.street = this.mapReceiver.street;
    }, 500);
  }
}
