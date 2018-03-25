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
  selector: 'view-order',
  templateUrl: 'view-order.component.html',
  styleUrls: ['view-order.component.css']
})
export class ViewOrderComponent implements OnInit {

  viewOrder: FormGroup;

  currentUser: User;
  order: Order;


  constructor(private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder,
              private orderService: OrderService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.currentUser().subscribe((user: User) => {
      const id = +this.activatedRouter.snapshot.paramMap.get('id');

      this.getOrder(id, user.id);
      this.currentUser = user;
    });




  }

  initForm(order : Order): FormGroup {
    return this.viewOrder = this.formBuilder.group({
        to: new FormControl({value:[order.receiverAddress.street , order.receiverAddress.house], disabled:true}),
        from: this.initFrom(order),
      creationTime: new FormControl({value:order.creationTime, disabled:true}),
      executionTime: new FormControl({value:order.executionTime, disabled:true}),
        description: new FormControl({value:order.description, disabled:true}),
        receiverAvailabilityTimeFrom: new FormControl({value: order.receiverAvailabilityTimeFrom, disabled: true}),
        receiverAvailabilityTimeTo: new FormControl({value: order.receiverAvailabilityTimeTo, disabled: true}),
        orderStatus : new FormControl({value:order.orderStatus, disabled:true}),
        feedback: new FormControl({value:'', disabled:order.orderStatus != 'WAITING_FOR_FEEDBACK'},[Validators.required, Validators.minLength(10), Validators.maxLength(256)])
    }
    );

  }

  initFrom(order:Order):FormControl{
    return order.office ? new FormControl({value:[ order.office.name], disabled:true}) : new FormControl({value:[order.senderAddress.street , order.senderAddress.house], disabled:true});
  }


  getOrder(orderId: number, userId: number) {
    this.orderService.getOrderById(orderId, userId)
      .subscribe((order: Order) => {

        this.order = order;

        this.initForm(order);

      });
  }


  saveFeedback(){
    this.orderService.saveFeedback(this.order).subscribe((order: Order) => {
      this.router.navigate(['orderHistory']);
    })
  }


  reRout(currentUserId: number) {
    this.orderService.getOrdersByUserId(currentUserId).subscribe(() => this.router.navigate(['/orderHistory/']));
  }

}
