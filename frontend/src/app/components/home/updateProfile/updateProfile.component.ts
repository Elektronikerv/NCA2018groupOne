import {Component, Injectable, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {AuthService} from "../../../service/auth.service";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {Router} from "@angular/router";
import {AddUpdAddressComponent} from "./address/addUpdAddress.component";

@Component({
  selector: 'app-update-profile',
  templateUrl: './updateProfile.component.html',
  styleUrls: ['./updateProfile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  user: User ;
  profileForm: FormGroup;
  addressForm: FormGroup;

  constructor(private location: Location,
              private router: Router,
              private authService: AuthService,
              private userService: UserService,
              private formBuilder: FormBuilder) {
    this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  ngOnInit() {

    this.profileForm = this.formBuilder.group({
      firstName: ['', CustomValidators.required],
      lastName: new FormControl(CustomValidators.required),
      phoneNumber: new FormControl(CustomValidators.required),
      email: new FormControl(CustomValidators.required),
      registrationDate: new FormControl(CustomValidators.required)
    }
      );
  }

  goBack(): void {
    this.location.back();
  }
  save(): void {
    console.log('Save() user: ' + this.user.firstName);
    this.userService.updateUserInfo(this.user)
      .subscribe((user: User) => { this.router.navigate(['home']);
  })
  }

  validateField(field: string): boolean {
    return this.profileForm.get(field).valid || !this.profileForm.get(field).dirty;
  }
}
