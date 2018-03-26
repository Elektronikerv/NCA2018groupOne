import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {AuthService} from "../../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../../model/user.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {Location} from "@angular/common";
import {UserService} from "../../../service/user.service";
import {PasswordService} from "../../../service/password.service";
import {GoogleMapsComponent} from "../../utils/google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {FLAT_PATTERN, FLOOR_PATTERN, PHONE_PATTERN} from "../../../model/utils";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import * as FileSaver from 'file-saver';
import {ReportService} from "../../../service/report.service";

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
  isEmployee: boolean;

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
              private ngZone: NgZone,
              private toasterService: ToasterService,
              private reportService: ReportService) {
    this.authService.currentUser().subscribe((user: User) => {
      this.user = user;
      if (user.roles.includes('CALL_CENTER_AGENT') || user.roles.includes('COURIER')) {
        this.isEmployee = true;
      }
    });
    this.map = new GoogleMapsComponent(mapsAPILoader, ngZone);

  }

  ngOnInit() {
    setTimeout(() => {
      this.map.setSearchElement(this.searchAddressRef);
    }, 700);
    this.map.ngOnInit();
    this.profileForm = this.formBuilder.group({
      firstName: new FormControl(CustomValidators.required, [Validators.maxLength(45), Validators.minLength(3)]),
      lastName: new FormControl(CustomValidators.required, [Validators.maxLength(45), Validators.minLength(3)]),
        phoneNumber: [CustomValidators.required, Validators.pattern(PHONE_PATTERN)],
        email: new FormControl([CustomValidators.required, CustomValidators.email]),
        registrationDate: new FormControl({value: '', disabled: true}, CustomValidators.required),
        address: this.initAddress()
      }
    );
  }

  public config: ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center',
    animation: 'fade'
  });

  popToast() {
    let toast: Toast = {
      type: 'success',
      title: 'Your profile is updated',
      body: '',
      showCloseButton: true
    };
    this.toasterService.pop(toast);
  }

  mapReady($event, yourLocation) {
    this.map.mapReady($event, yourLocation);
    setTimeout(() => {
      this.map.geocodeAddress(this.user.address.street, this.user.address.house);
    }, 800);
  }

  initAddress() {
    this.addressForm = this.formBuilder.group({
      street: new FormControl([CustomValidators.required, Validators.minLength(5)]),
      house: new FormControl([CustomValidators.required, Validators.maxLength(5)]),
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  save(): void {
    console.log('Save() user: ' + this.user.firstName);
    this.userService.updateUserInfo(this.user)
      .subscribe((user: User) => {
        this.router.navigate(['home']);
        this.popToast();
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

  generatePersonalInformationReport() {
    this.reportService.getPersonalInformationReport(this.authService.currentUserId()).subscribe(
      (res: any) => {
        let filename = 'Personal information for ' + this.authService.currentUserId() + '.pdf';
        FileSaver.saveAs(res, filename);
      }
    );
  }

  updateStreet() {
    this.user.address.street = this.map.street;
  }

  updateHouse() {
    this.user.address.house = this.map.house;
  }

  updateStreetHouse() {
    setTimeout(() => {
      this.user.address.house = this.map.house;
      this.user.address.street = this.map.street;
    }, 500);
  }
}
