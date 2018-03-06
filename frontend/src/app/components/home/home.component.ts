import {Component, NgZone, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../model/user.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {Location} from "@angular/common";
import {UserService} from "../../service/user.service";
import {PasswordService} from "../../service/password.service";
import {GoogleMapsComponent} from "../google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";

@Component({
  moduleId: module.id,
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent extends GoogleMapsComponent implements OnInit {
  user: User = UserService.getEmptyUser();
  password: string;
  profileForm: FormGroup;
  addressForm: FormGroup;
  passwordForm: FormGroup;

  constructor(private authService: AuthService,
              private activatedRouter: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private location: Location,
              private userService: UserService,
              private passwordService: PasswordService,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
    super(mapsAPILoader, ngZone);
    this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  ngOnInit() {
    super.ngOnInit();
    this.profileForm = this.formBuilder.group({
        firstName: new FormControl(CustomValidators.required),
        lastName: new FormControl(CustomValidators.required),
        phoneNumber: new FormControl([CustomValidators.required, CustomValidators.email]),
        email: new FormControl(CustomValidators.required),
        registrationDate: new FormControl({value: '', disabled: true}, CustomValidators.required),
        address: this.initAddress()
      }
    );
  }

  fillStreetAndHouse(newAddress : string){
    this.inputAddress = newAddress;
    this.user.address.street = this.inputAddress.split(',')[0].trim();
    this.user.address.house = this.inputAddress.split(',')[1].trim();
  }

  mapReady($event) {
    $event.controls[google.maps.ControlPosition.RIGHT_CENTER].push(document.getElementById('your_location'));
    $event.controls[google.maps.ControlPosition.TOP_CENTER].push(document.getElementById('inputSearch'));
    this.geocodeAddress(this.user.address.street, this.user.address.house);
  }

  initAddress() {
    this.addressForm = this.formBuilder.group({
      street: new FormControl([CustomValidators.required, Validators.minLength(5)]),
      house: new FormControl([CustomValidators.required, Validators.maxLength(5)]),
      floor: new FormControl([CustomValidators.required, CustomValidators.range(-20, 200)]),
      flat: new FormControl([CustomValidators.required, CustomValidators.min(-20), CustomValidators.max(200)])
    });
  }

  save(): void {
    console.log('Save() user: ' + this.user.firstName);
    this.userService.updateUserInfo(this.user)
      .subscribe((user: User) => {
        this.router.navigate(['home']);
      })
  }

  validateField(field: string): boolean {
    return this.profileForm.get(field).valid || !this.profileForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressForm.get(field).valid || !this.addressForm.get(field).dirty;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/signin']);
  }
}
