import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators, FormControl} from "@angular/forms";
import {OrderService} from "../../../service/order.service";
import {Order} from "../../../model/order.model";
import {OfficeService} from "../../../service/office.service";
import {Office} from '../../../model/office.model';
import {OrderStatus} from '../../../model/orderStatus.model';
import {CustomValidators} from "ng2-validation";
import {ORDER_STATUSES} from '../../../model/orderStatus.model';
import {FulfillmentOrder} from '../../../model/fulfillmentOrder.model';
import {User} from '../../../model/user.model';
import {Address} from "../../../model/address.model";
import {FLAT_PATTERN, FLOOR_PATTERN} from "../../../model/utils";

@Component({
  selector: 'app-o-c-edit-order-ccagent',
  templateUrl: './edit-o-c-order-ccagent.component.html',
  styleUrls: ['./edit-o-c-order-ccagent.component.css']
})
export class EditOCOrderCcagentComponent implements OnInit {

  fulfillmentOrder: FulfillmentOrder = <FulfillmentOrder>{};
  offices: Office[];
  orderForm: FormGroup;
  senderAddress: FormGroup;
  receiverAddress: FormGroup;
  couriers: User[];
  receiverAvailabilityFrom: string = '';
  receiverAvailabilityTo: string = '';
  receiverAvailabilityDate: string = '';
  officeId: number;

  constructor(private orderService: OrderService,
              private activatedRouter: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private officeService: OfficeService) {
    this.fulfillmentOrder.order = <Order>{};
    this.fulfillmentOrder.order.user = <User>{};
    this.fulfillmentOrder.order.senderAddress = <Address>{};
    this.fulfillmentOrder.order.receiverAddress = <Address>{};


  }

  ngOnInit(): void {
    this.getFulfillmentOrder();
    this.getOffices();
    // this.getCouriers();
    this.initForm(this.fulfillmentOrder);

  }

  initForm(fulfillment: FulfillmentOrder) {
    this.orderForm = this.formBuilder.group({
        receiverAddress: this.initReceiverAddress(),
        office: new FormControl(null, [Validators.required]),
        description: new FormControl(CustomValidators.required),
        receiverAvailabilityDate: [Validators.required],
        receiverAvailabilityFrom: [Validators.required],
        receiverAvailabilityTo: [Validators.required],
        receiverAvailabilityTimeFrom: new FormControl(),
        receiverAvailabilityTimeTo: new FormControl()
      }
    );
  }

  initReceiverAddress(): FormGroup {
    return  this.receiverAddress =this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [0, [Validators.required, Validators.pattern(FLOOR_PATTERN)]],
      flat: [0, [Validators.required, Validators.pattern(FLAT_PATTERN)]]
    });
  }





  getFulfillmentOrder() {
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    console.log('getOrder() id: ' + id);
    this.orderService.getFulfillmentOrderById(id)
      .subscribe((order: FulfillmentOrder) => {
        this.fulfillmentOrder = order;
        console.log("get FULL ORDER " + JSON.stringify(this.fulfillmentOrder));
        this.officeId = order.order.office ?  order.order.office.id : 0;

        // this.receiverAvailabilityFrom = order.order.receiverAvailabilityTimeFrom.toDateString() ;
        // this.receiverAvailabilityTo = order.order.receiverAvailabilityTimeFrom.toDateString() ;
        // this.receiverAvailabilityDate = order.order.receiverAvailabilityTimeFrom.toDateString() ;
      });

  }

  getOffices() {
    this.officeService.getOffices()
      .subscribe(offices => this.offices = offices);

  }

  getCouriers() {
    this.orderService.getAllCouriers()
      .subscribe(couriers => this.couriers = couriers);

  }

  confirmOrder() {
    console.log("ccagent id" + this.fulfillmentOrder.ccagent.id);
    // this.fullFillmentOrder.order.orderStatus = "CONFIRMED";
    this.fulfillmentOrder.order.receiverAvailabilityTimeFrom = this.receiverAvailabilityDate + ' ' + this.receiverAvailabilityFrom + ':00';
    this.fulfillmentOrder.order.receiverAvailabilityTimeTo = this.receiverAvailabilityDate + ' ' + this.receiverAvailabilityTo + ':00';

    this.orderService.confirmFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  cancelOrder() {
    this.fulfillmentOrder.order.orderStatus = "CANCELLED"; // Move this action to UI
    this.update();
  }

  update() {
    this.orderService.updateFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddress.get(field).valid || !this.senderAddress.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }

}
