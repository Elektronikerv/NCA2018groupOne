import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {Office} from '../../../../model/office.model';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from 'ng2-validation';
import {OfficeService} from '../../../../service/office.service';
import {GoogleMapsComponent} from '../../../utils/google-maps/google-maps.component';
import {MapsAPILoader} from '@agm/core';
import {Address} from "../../../../model/address.model";
import {FLAT_PATTERN, FLOOR_PATTERN} from "../../../../model/utils";

@Component({
  moduleId: module.id,
  selector: 'cudOffice',
  templateUrl: 'cudOffice.component.html',
  styleUrls: ['cudOffice.component.css']
})
export class CudOfficeComponent implements OnInit {
  office : Office = <Office>{};
  cudOfficeForm: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;
  map: GoogleMapsComponent;

  @ViewChild('searchAddress')
  public searchAddressRef: ElementRef;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private officeService: OfficeService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone) {
    this.map = new GoogleMapsComponent(mapsAPILoader,ngZone);
    this.office.address = <Address>{}
  }

  ngOnInit(): void {
    this.map.setSearchElement(this.searchAddressRef);
    this.map.ngOnInit();
    this.cudOfficeForm = this.formBuilder.group({
        name: ['', [Validators.required, Validators.minLength(5)]],
        address: this.initAddress(),
        description: [''],
      }
    );
  }

  initAddress() {
    return this.addressOfficeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  save(): void {
    console.log('createOffice(office: Office) office: ' + this.office.name);
    this.officeService.createOffice(this.office).subscribe((office: Office) => {
      this.router.navigate(['admin/adminOffice']);
    });
  }

  validateField(field: string): boolean {
    return this.cudOfficeForm.get(field).valid || !this.cudOfficeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressOfficeRegisterByAdmin.get(field).valid || !this.addressOfficeRegisterByAdmin.get(field).dirty;
  }

  updateStreetHouse() {
    setTimeout(() => {
      this.office.address.street = this.map.street;
      this.office.address.house = this.map.house;
    }, 500);
  }
}
