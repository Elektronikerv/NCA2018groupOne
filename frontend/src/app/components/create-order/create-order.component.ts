import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {OrderService} from "../../service/order.service";
import {Order} from "../../model/order.model";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {Address} from '../../model/address.model';

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
  currentUser: User;
  order: Order;


  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.order = <Order>{};
    this.order.senderAddress = <Address>{};
    this.order.receiverAddress = <Address>{};
    console.log('Init create-order');
    this.authService.currentUser().subscribe((user: User) => this.currentUser = user);
    this.createOrderForm = this.formBuilder.group({
      senderAddress: this.initSenderAddress(),
      receiverAddress: this.initReceiverAddress(),
      description: ['']
    })
  }

  initSenderAddress() {
    return this.senderAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]]
    });
  }

  initReceiverAddress() {
    return this.receiverAddress = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]]
    });
  }

  createOrder(order: Order): void {
    console.log("Create order");
    order.user = this.currentUser;
    order.orderStatus = {id: 7, name: "OPEN", description: "OPEN"};
    this.orderService.create(order).subscribe((order: Order) => {
      console.log("Created OPEN order number " + order.id + " for user " + this.currentUser.id);
      this.router.navigate(['order-history/' + this.currentUser.id]);
    })
  }

  createDraft(): void {
    console.log('Create draft: ' + JSON.stringify(this.order));
    this.order.user = this.currentUser;
    this.order.orderStatus = {id: 1, name: "DRAFT", description: "DRAFT"};
    this.orderService.create(this.order).subscribe((order: Order) => {
      console.log("Created draft number " + order.id + " for user " + this.currentUser.id);
      this.router.navigate(['order-history/' + this.currentUser.id]);
    })
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
