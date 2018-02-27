import {Component, Injectable, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {Router} from "@angular/router";
import {User} from "../../../../model/user.model";
import {AuthService} from "../../../../service/auth.service";
import {Address} from "../../../../model/address.model";
import {UserService} from "../../../../service/user.service";

@Component({
  moduleId: module.id,
  selector: 'add-upd-address',
  templateUrl: 'addUpdAddress.component.html',
  styleUrls: ['addUpdAddress.component.css']
})
export class AddUpdAddressComponent implements OnInit {
  user: User ;
  addressForm: FormGroup;

  constructor(private location: Location,
              private router: Router,
              private authService: AuthService,
              private userService: UserService,
              private formBuilder: FormBuilder) {
    this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  ngOnInit() {
    this.addressForm = this.formBuilder.group({
      street:  [CustomValidators.required, Validators.minLength(5)],
      house: [CustomValidators.required, Validators.maxLength(5)],
      floor: [CustomValidators.range(-20, 200)],
      flat: [CustomValidators.min(-20), CustomValidators.max(200)]
    });
  }

  updAddress(address: Address){
    console.log('UpdAddress() address street: ' + address.street);
    this.user.address = address;
    this.userService.updateUserInfo(this.user)
      .subscribe((user: User) => { this.router.navigate(['home']);
      })
  }

  validateField(field: string): boolean {
    return this.addressForm.get(field).valid || !this.addressForm.get(field).dirty;
  }

  goBack(): void {
    this.location.back();
  }

}
