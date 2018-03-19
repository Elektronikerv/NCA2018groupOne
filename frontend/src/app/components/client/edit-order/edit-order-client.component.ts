import { Component, OnInit, Input } from '@angular/core';
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

@Component({
  moduleId: module.id,
  selector: 'app-edit-order-client',
  templateUrl: 'edit-order-client.component.html',
  styleUrls: ['edit-order-client.component.css']
})
export class EditOrderClientComponent implements OnInit {

  private jwtHelper: JwtHelper = new JwtHelper();
  order: Order = <Order>{};
  offices: Office[];
  orderForm: FormGroup;
  senderAddressForm: FormGroup;
  receiverAddressForm: FormGroup;
  receiverAvailabilityFrom :string = '';
  receiverAvailabilityTo :string = '';
  receiverAvailabilityDate :string = '';
  office1 : Office;
  currentUserId : number;

  constructor( private orderService: OrderService,
               private activatedRouter: ActivatedRoute,
               private router: Router,
               private formBuilder: FormBuilder,
               private officeService: OfficeService) {
  }

  ngOnInit(): void {

    this.getOrder();
    this.getOffices();
    let token = localStorage.getItem("currentUser");
    this.currentUserId = +this.jwtHelper.decodeToken(token).id;
    this.initForms();

  }

  initForms(){
    this.orderForm = this.formBuilder.group({
        office: new FormControl(),
        description : new FormControl(CustomValidators.required),
        receiverAvailabilityDate: [Validators.required],
        receiverAvailabilityFrom:[Validators.required],
        receiverAvailabilityTo:[ Validators.required],
      senderAddress: this.initSenderAddressForm(),
      receiverAddress: this.initReceiverAddressForm(),
      }
    );
  }

  initSenderAddressForm(): FormGroup {
    return this.senderAddressForm = this.initAddress();
  }
  initReceiverAddressForm(): FormGroup {
    return this.receiverAddressForm = this.initAddress();
  }

  initAddress(): FormGroup  {
    return  this.formBuilder.group({
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
