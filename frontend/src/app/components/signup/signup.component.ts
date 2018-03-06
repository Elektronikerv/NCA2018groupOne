import { Component, NgZone,  OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import {PasswordService} from "../../service/password.service";
import {GoogleMapsComponent} from "../google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
@Component({
    moduleId: module.id,
    selector: 'signup',
    templateUrl:'signup.component.html',
    styleUrls: ['signup.component.css']
    })
export class SignupComponent   implements OnInit{
  userRegisterForm: FormGroup;
  addressForm: FormGroup;
  user : User = UserService.getEmptyUser();
  map :GoogleMapsComponent;

  constructor(private userService: UserService,
              private router: Router,
              private formBuilder: FormBuilder,
              private toasterService: ToasterService,
              private passwordService: PasswordService,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone
  ) {
    this.map = new GoogleMapsComponent (mapsAPILoader, ngZone);
  }

  ngOnInit() {
    this.map.ngOnInit();
    this.userRegisterForm = this.formBuilder.group({
      firstName: new FormControl(CustomValidators.required, Validators.maxLength(256)),
      lastName: new FormControl(CustomValidators.required, Validators.maxLength(256)),
      email: ['', [Validators.required, CustomValidators.email]],
      phoneNumber: ['', CustomValidators.phone('UA','US', 'International')],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
      address : this.initAddress(),
    } , {validator: this.passwordService.passwordConfirming} );

  }

  initAddress() {
      this.addressForm = this.formBuilder.group({
        street: ['', [Validators.required, Validators.minLength(5)]],
        house: ['', [Validators.required, Validators.maxLength(5)]],
        floor: ['', [CustomValidators.min(-20), CustomValidators.max(200)]],
        flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]] });
  }


  fillStreetAndHouse(newAddress : string){
        this.map.inputAddress = newAddress;
        this.user.address.street = this.map.inputAddress.split(',')[0].trim();
        this.user.address.house = this.map.inputAddress.split(',')[1].trim();
      }


  public config1 : ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center'
  });


  submitForm(): void{
    console.log(this.user);
    this.userService.create(this.user).subscribe((user: User) => {
      this.popToast();
      this.router.navigate(['/landing']);
    });
  }

  popToast() {
    var toast: Toast = {
      type: 'info',
      title: 'Hello from Toast Title',
      body: 'Hello from Toast Body'
    };
    this.toasterService.pop(toast);
  }

  validateField(field: string): boolean {
    console.log(this.userRegisterForm.get(field));
    return this.userRegisterForm.get(field).valid || !this.userRegisterForm.get(field).dirty;
  }

  checkPass(): boolean{
    return this.userRegisterForm.get(['password']).value != this.userRegisterForm.get(['confirmPassword']).value && this.userRegisterForm.get(['confirmPassword']).value != null;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressForm.get(field).valid || !this.addressForm.get(field).dirty;
  }


}
