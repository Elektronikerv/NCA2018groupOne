import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
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
export class HomeComponent implements OnInit {
  user: User = UserService.getEmptyUser();
  password: string;
  profileForm: FormGroup;
  addressForm: FormGroup;
  passwordForm: FormGroup;
  map: GoogleMapsComponent;

  @ViewChild('searchAddress')
  public searchAddressRef: ElementRef;

  constructor(private authService: AuthService,
              private activatedRouter: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private location: Location,
              private userService: UserService,
              private passwordService: PasswordService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone) {
    this.authService.currentUser().subscribe((user: User) => this.user = user);
    this.map = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit() {
    setTimeout(() => {
      this.map.setSearchElement(this.searchAddressRef);
      this.map.ngOnInit();
    }, 700);
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

  updateStreetHouse() {
    setTimeout(() => {
      this.user.address.street = this.map.street;
      this.user.address.house = this.map.house;
    }, 500);
  }

  mapReady($event, yourLocation, inputSearch) {
    this.map.mapReady($event, yourLocation, inputSearch);
    setTimeout(() => {
      this.map.geocodeAddress(this.user.address.street, this.user.address.house);
    }, 800);
  }

  initAddress() {
    this.addressForm = this.formBuilder.group({
      street: new FormControl([CustomValidators.required, Validators.minLength(5)]),
      house: new FormControl([CustomValidators.required, Validators.maxLength(5)]),
      floor: ['', [CustomValidators.min(-20), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(1000)]]
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
