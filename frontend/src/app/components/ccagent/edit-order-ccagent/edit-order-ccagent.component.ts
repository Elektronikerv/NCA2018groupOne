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
@Component({
  selector: 'app-edit-order-ccagent',
  templateUrl: './edit-order-ccagent.component.html',
  styleUrls: ['./edit-order-ccagent.component.css']
})
export class EditOrderCcagentComponent implements OnInit {

  @Input() order: Order;
  offices: Office[];
  orderForm: FormGroup;
  senderAddressForm: FormGroup;
  receiverAddressForm: FormGroup;

  statuses = ORDER_STATUSES;

  constructor(private orderService: OrderService,
               private activatedRouter: ActivatedRoute,
               private router: Router,
               private formBuilder: FormBuilder,
               private officeService: OfficeService) { }


  ngOnInit(): void {
    this.getOrder();
    // this.order.orderStatus = ORDER_STATUSES[4]; // set PROCESSING
    // this.update();
    this.getOffices();
    this.receiverAddressForm = this.initAddress();
    this.senderAddressForm = this.initAddress();


    this.orderForm = this.formBuilder.group({
      senderAddress: this.senderAddressForm,
      receiverAddress: this.receiverAddressForm,
      office: new FormControl(),
      description : new FormControl(CustomValidators.required)
      }
    );

  }

  customCompare(o1: Office, o2: Office) {
    return o1.id == o2.id
  }

  initAddress(): FormGroup  {
     return  this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(0), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]]
    });
  }

  getOrder() {
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    console.log('getOrder() id: ' + id);
    this.orderService.getOrderById(id).subscribe((order: Order) => {this.order = order;
    console.log("get order " + JSON.stringify(this.order))});
  }

  getOffices() {
    this.officeService.getOffices()
        .subscribe(offices => this.offices = offices);

  }

  confirmOrder() {
    this.order.orderStatus = ORDER_STATUSES[6].name; //CONFIRMED
    this.update();
  }

  cancelOrder() {
    this.order.orderStatus = ORDER_STATUSES[1].name; //CANCELLED
    this.update();
  }

  update() {
    // console.log("updated order" + JSON.stringify(this.order));
    this.orderService.update(this.order)
      .subscribe(_ => this.router.navigate(['ccagent/orders']));
  }

  validateFieldSenderAddress(field: string): boolean {
    return this.senderAddressForm.get(field).valid || !this.senderAddressForm.get(field).dirty;
  }

  validateFieldReceiverAddress(field: string): boolean {
    return this.receiverAddressForm.get(field).valid || !this.receiverAddressForm.get(field).dirty;
  }

}
