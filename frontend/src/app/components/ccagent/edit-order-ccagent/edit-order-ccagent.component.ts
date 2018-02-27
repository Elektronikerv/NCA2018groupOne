import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { OrderService } from "../../../service/order.service";
import { Order } from "../../../model/order.model";


@Component({
  selector: 'app-edit-order-ccagent',
  templateUrl: './edit-order-ccagent.component.html',
  styleUrls: ['./edit-order-ccagent.component.css']
})
export class EditOrderCcagentComponent implements OnInit {

  @Input()
  order: Order;
  orderForm: FormGroup;
  addressForm: FormGroup;

  constructor( private orderService: OrderService, 
               private router: ActivatedRoute,
               private formBuilder: FormBuilder) { }


  ngOnInit(): void {
    this.getOrder();
    this.orderForm = this.formBuilder.group({
        address: this.initAddress(),
      }
    );
  }

  initAddress() {
    return this.addressForm = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [''],
      flat: ['']
    });
  }

  getOrder() {
    const id = +this.router.snapshot.paramMap.get('id');
    console.log('getOrder() id: ' + id);
    this.orderService.getOrderById(id).map(order => this.order = order);
  }



}
