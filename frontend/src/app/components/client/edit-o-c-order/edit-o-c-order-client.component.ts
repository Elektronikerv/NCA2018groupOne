import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
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
import {DateValidatorService} from "../../../service/date-validator.service";

@Component({
  moduleId: module.id,
  selector: 'app-o-c-edit-order-client',
  templateUrl: 'edit-o-c-order-client.component.html',
  styleUrls: ['edit-o-c-order-client.component.css']
})
export class EditOCOrderClientComponent implements OnInit {

  orderForm: FormGroup;
  receiverAddress: FormGroup;

  currentUser: User;
  order: Order;
  offices: Office[] = <Office[]>{};
  officeId: number;

  mapTo: GoogleMapsComponent;

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
              private dateValidatorService: DateValidatorService) {
    this.mapTo = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    this.authService.currentUser().subscribe((user: User) => {
      const id = +this.activatedRouter.snapshot.paramMap.get('id');

      this.getOrder(id, user.id);
      this.currentUser = user;
    });
    this.mapTo.setSearchElement(this.searchAddressToRef);
    this.mapTo.ngOnInit();

    this.getOffices();
    this.initForm();

  }

  initForm(): FormGroup {
    return this.orderForm = this.formBuilder.group({
        receiverAddress: this.initReceiverAddress(),
        office: new FormControl(null, [Validators.required]),
        description: [''],
        receiverAvailabilityDate: ['', [Validators.required]],
        receiverAvailabilityFrom: ['', [Validators.required]],
        receiverAvailabilityTo: ['', [Validators.required]],
        receiverAvailabilityTimeFrom: new FormControl(),
        receiverAvailabilityTimeTo: new FormControl()
      }, {
        validator: [
          this.dateValidatorService.currentDayValidator('receiverAvailabilityDate'),
          this.dateValidatorService.timeFromValidator('receiverAvailabilityDate', 'receiverAvailabilityFrom'),
          this.dateValidatorService.timeRangeValidator('receiverAvailabilityFrom', 'receiverAvailabilityTo')]
      }
    );

  }


  getOrder(orderId: number, userId: number) {
    this.orderService.getOrderById(orderId, userId)
      .subscribe((order: Order) => {
        this.officeId = order.office.id;
        this.order = order;
        this.order.receiverAvailabilityDate = this.order.receiverAvailabilityTimeTo == null ? '' : this.order.receiverAvailabilityTimeTo.toString().substring(0, 10);
        this.order.receiverAvailabilityFrom = this.order.receiverAvailabilityTimeFrom == null ? '' : this.order.receiverAvailabilityTimeFrom.toString().substring(11, 16);
        this.order.receiverAvailabilityTo = this.order.receiverAvailabilityTimeTo == null ? '' : this.order.receiverAvailabilityTimeTo.toString().substring(11, 16);

        this.officeId = order.office.id;
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


  cancelOrder() {
    this.orderService.cancelOrder(this.order).subscribe((order: Order) => {
      this.router.navigate(['orderHistory']);
    });
  }

  deleteDraft() {
    this.orderService.deleteDraft(this.order).subscribe(() => {
      this.reRout(this.currentUser.id);
    })
  }

  saveOpenOrder(order: any) {
    this.order.receiverAvailabilityTimeFrom = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityFrom + ':00';
    this.order.receiverAvailabilityTimeTo = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityTo + ':00';
    this.update()
  }

  saveDraft(order: any) {
    if (order.receiverAvailabilityDate != '' && order.receiverAvailabilityFrom != '' && order.receiverAvailabilityDate != null && order.receiverAvailabilityFrom != null) {
      order.receiverAvailabilityTimeFrom = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityFrom + ':00';

    }
    if (order.receiverAvailabilityDate != null && order.receiverAvailabilityTo != null && order.receiverAvailabilityDate != '' && order.receiverAvailabilityTo != '') {
      order.receiverAvailabilityTimeTo = order.receiverAvailabilityDate + ' ' + order.receiverAvailabilityTo + ':00';

    }
    this.update()
  }

  update() {
    this.orderService.update(this.order).subscribe((order: Order) => {
      this.router.navigate(['orderHistory']);
    })
  }


  getOffices() {
    this.officeService.getOffices()
      .subscribe((offices: Office[]) => this.offices = offices);

  }

  validateField(field: string): boolean {
    return this.orderForm.get(field).valid || !this.orderForm.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
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
    this.orderService.getOrdersByUserId(currentUserId).subscribe(() => this.router.navigate(['/orderHistory/']));
  }

}
