import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {OrderService} from '../../../service/order.service';
import {Order} from '../../../model/order.model';
import {OfficeService} from '../../../service/office.service';
import {Office} from '../../../model/office.model';
import {User} from '../../../model/user.model';
import {FLAT_PATTERN, FLOOR_PATTERN} from '../../../model/utils';
import {GoogleMapsComponent} from '../../utils/google-maps/google-maps.component';
import {MapsAPILoader} from '@agm/core';
import {AuthService} from '../../../service/auth.service';
import {DateValidatorService} from "../../../service/date-validator.service";
import {CustomToastService} from "../../../service/customToast.service";

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
  currentOffice: Office;

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
              private dateValidatorService: DateValidatorService,
              private customToastService: CustomToastService) {
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
          this.dateValidatorService.timeRangeValidator('receiverAvailabilityFrom', 'receiverAvailabilityTo'),
          this.dateValidatorService.maximumDaysOfCreatingOrderInAdvanceValidator('receiverAvailabilityDate')]

      }
    );
  }

  compareOffices(office1: number, office2: number) {
    return office1 == office2;
  }

  getOrder(orderId: number, userId: number) {
    this.orderService.getOrderById(orderId, userId)
      .subscribe((order: Order) => {
        this.order = order;
        this.currentOffice = order.office;
        this.order.receiverAvailabilityDate = this.order.receiverAvailabilityTimeTo == null ? this.order.receiverAvailabilityTimeFrom.toString().substring(0, 10) : this.order.receiverAvailabilityTimeTo.toString().substring(0, 10);
        this.order.receiverAvailabilityFrom = this.order.receiverAvailabilityTimeFrom == null ? '' : this.order.receiverAvailabilityTimeFrom.toString().substring(11, 16);
        this.order.receiverAvailabilityTo = this.order.receiverAvailabilityTimeTo == null ? '' : this.order.receiverAvailabilityTimeTo.toString().substring(11, 16);
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

  cancelOrder() {
    this.orderService.cancelOrder(this.order).subscribe((order: Order) => {
      this.customToastService.setMessage('Order: '+ this.order.id +', is canceled');
      this.router.navigate(['orderHistory']);
    });
  }

  deleteDraft() {
    this.orderService.deleteDraft(this.order).subscribe(() => {
      this.customToastService.setMessage('Draft is deleted');
      this.reRout(this.currentUser.id);
    })
  }

  confirmOrderFromDraft(){
    this.order.receiverAvailabilityTimeFrom = this.order.receiverAvailabilityDate + ' ' + this.order.receiverAvailabilityFrom + ':00';
    this.order.receiverAvailabilityTimeTo = this.order.receiverAvailabilityDate + ' ' + this.order.receiverAvailabilityTo + ':00';

    this.orderService.createOrderFromDraft(this.order).subscribe((order1: Order) => {
      this.customToastService.setMessage('Order is created. Our operator will call you as soon as possible for confirmation your order.');
      this.router.navigate(['orderHistory']);
    });
  }

  save() {
    this.order.orderStatus != 'OPEN' ? this.saveDraft() : this.saveOpenOrder();
  }

  saveOpenOrder() {
    this.order.receiverAvailabilityTimeFrom = this.order.receiverAvailabilityDate + ' ' + this.order.receiverAvailabilityFrom + ':00';
    this.order.receiverAvailabilityTimeTo = this.order.receiverAvailabilityDate + ' ' + this.order.receiverAvailabilityTo + ':00';
    this.update()
  }

  saveDraft() {
    if (this.order.receiverAvailabilityDate != '' && this.order.receiverAvailabilityFrom != '' && this.order.receiverAvailabilityDate != null && this.order.receiverAvailabilityFrom != null) {
      this.order.receiverAvailabilityTimeFrom = this.order.receiverAvailabilityDate + ' ' + this.order.receiverAvailabilityFrom + ':00';

    }
    if (this.order.receiverAvailabilityDate != null && this.order.receiverAvailabilityTo != null && this.order.receiverAvailabilityDate != '' && this.order.receiverAvailabilityTo != '') {
      this.order.receiverAvailabilityTimeTo = this.order.receiverAvailabilityDate + ' ' + this.order.receiverAvailabilityTo + ':00';
    }
    this.update()
  }

  update() {
    this.orderService.update(this.order).subscribe((order: Order) => {
      this.customToastService.setMessage('Order is updated');
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
