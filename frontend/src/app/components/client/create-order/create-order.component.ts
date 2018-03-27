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
import {DateValidatorService} from "../../../service/date-validator.service";
import {CustomToastService} from "../../../service/customToast.service";

@Component({
  moduleId: module.id,
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {
  createOrderForm: FormGroup;
  senderAddress: FormGroup;
  receiverAddress: FormGroup;
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
              private ngZone: NgZone,
              private dateValidatorService: DateValidatorService,
              private customToastService: CustomToastService) {
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
    this.createOrderForm.reset();
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
      }, {
        validator: [this.dateValidatorService.currentDayValidator('receiverAvailabilityDate'),
          this.dateValidatorService.timeFromValidator('receiverAvailabilityDate', 'receiverAvailabilityFrom'),
          this.dateValidatorService.timeRangeValidator('receiverAvailabilityFrom', 'receiverAvailabilityTo'),
          this.dateValidatorService.maximumDaysOfCreatingOrderInAdvanceValidator('receiverAvailabilityDate')]
      }
    );
  }

  initOfficeForm(): FormControl {
    return new FormControl(null, [Validators.required]);
  }

  initSenderAddress(): FormGroup {
    return this.senderAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  initEmptySenderAddress(): FormGroup {
    return this.senderAddress = this.formBuilder.group({
      street: new FormControl(''),
      house: new FormControl(''),
      floor: new FormControl(),
      flat: new FormControl()
    });
  }

  initReceiverAddress(): FormGroup {
    return this.receiverAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  refreshOfficeForm() {
    this.isOfficeClientDelivery = true;
    // this.createOrderForm.get('senderAddress').reset();
    this.createOrderForm.removeControl('senderAddress');
    this.createOrderForm.addControl('senderAddress', this.initEmptySenderAddress());
    this.createOrderForm.get('senderAddress').reset();
    this.createOrderForm.setControl('office', this.initOfficeForm());
  }

  refreshSenderForm() {
    this.isOfficeClientDelivery = false;
    this.createOrderForm.setControl('office', this.initEmptyOfficeForm());
    this.createOrderForm.get('office').reset();
    // this.createOrderForm.get('senderAddress').reset();
    this.createOrderForm.removeControl('senderAddress');
    this.createOrderForm.addControl('senderAddress', this.initSenderAddress());
    this.createOrderForm.get('senderAddress').reset();
  }

  initEmptyOfficeForm(): FormControl {
    return new FormControl();
  }


  createOrder(order: any): void {
    order.user = this.currentUser;

    order.receiverAvailabilityTimeFrom = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityFrom + ':00';
    order.receiverAvailabilityTimeTo = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityTo + ':00';

    this.orderService.createOrder(order).subscribe((order1: Order) => {
      this.customToastService.setMessage('Order is created. Our operator will call you as soon as possible for confirmation your order.');
      this.router.navigate(['orderHistory']);
    });
  }

  createDraft(order: any): void {
    order.user = this.currentUser;
    if (order.receiverAvailabilityDate != '' && order.receiverAvailabilityFrom != '' && order.receiverAvailabilityDate != null && order.receiverAvailabilityFrom != null) {
      order.receiverAvailabilityTimeFrom = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityFrom + ':00';

    }
    if (order.receiverAvailabilityDate != null && order.receiverAvailabilityTo != null && order.receiverAvailabilityDate != '' && order.receiverAvailabilityTo != '') {
      order.receiverAvailabilityTimeTo = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityTo + ':00';

    }

    this.orderService.createDraft(order).subscribe((order: Order) => {
      this.customToastService.setMessage('Order added as draft.');
      this.router.navigate(['orderHistory']);
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
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }


}
