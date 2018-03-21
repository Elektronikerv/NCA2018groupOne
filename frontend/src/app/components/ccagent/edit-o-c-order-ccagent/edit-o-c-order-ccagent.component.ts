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
import {date} from "ng2-validation/dist/date";

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


  }

  initForm() {
    this.orderForm = this.formBuilder.group({
        receiverAddress: this.initReceiverAddress(),
        office: new FormControl(null, [Validators.required]),
        description: new FormControl(CustomValidators.required),
        receiverAvailabilityDate:['' ,[Validators.required]],
        receiverAvailabilityFrom: ['',[Validators.required]],
        receiverAvailabilityTo: ['',[Validators.required]],
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
    this.orderService.getFulfillmentOrderById(id)
      .subscribe((order: FulfillmentOrder) => {
        this.fulfillmentOrder = order;
        this.fulfillmentOrder.order.receiverAvailabilityDate = this.fulfillmentOrder.order.receiverAvailabilityTimeTo.toString().substring(0,10);
        this.fulfillmentOrder.order.receiverAvailabilityFrom = this.fulfillmentOrder.order.receiverAvailabilityTimeFrom.toString().substring(11,16);
        this.fulfillmentOrder.order.receiverAvailabilityTo = this.fulfillmentOrder.order.receiverAvailabilityTimeTo.toString().substring(11,16);
        this.initForm();
        this.officeId = order.order.office ?  order.order.office.id : 0;

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

  confirmOrder(order : Order) {
    this.updateAvailabilityTime();
    this.orderService.confirmFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  cancelOrder() {
    this.updateAvailabilityTime();
    this.orderService.cancelFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']))
  }

  save(order : Order){
    this.updateAvailabilityTime();
    this.update();
  }

  update() {
    this.orderService.updateFulfillmentOrder(this.fulfillmentOrder)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  private updateAvailabilityTime(){
    console.log('From ' + this.fulfillmentOrder.order.receiverAvailabilityTimeFrom );
    console.log('TO ' + this.fulfillmentOrder.order.receiverAvailabilityTimeTo );
    this.fulfillmentOrder.order.receiverAvailabilityTimeFrom =  this.fulfillmentOrder.order.receiverAvailabilityDate + ' ' + this.fulfillmentOrder.order.receiverAvailabilityFrom + ':00';
    this.fulfillmentOrder.order.receiverAvailabilityTimeTo =  this.fulfillmentOrder.order.receiverAvailabilityDate + ' ' + this.fulfillmentOrder.order.receiverAvailabilityTo + ':00';
    console.log('From ' + this.fulfillmentOrder.order.receiverAvailabilityTimeFrom );
    console.log('TO ' + this.fulfillmentOrder.order.receiverAvailabilityTimeTo );

  }


  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddress.get(field).valid || !this.senderAddress.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddress.get(field).valid || !this.receiverAddress.get(field).dirty;
  }

}
