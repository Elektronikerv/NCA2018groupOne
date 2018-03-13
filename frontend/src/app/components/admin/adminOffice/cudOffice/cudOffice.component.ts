import {Component, NgZone, OnInit} from '@angular/core';
import {Office} from '../../../../model/office.model';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from 'ng2-validation';
import {OfficeService} from '../../../../service/office.service';
import {GoogleMapsComponent} from '../../../google-maps/google-maps.component';
import {MapsAPILoader} from '@agm/core';
import {Address} from "../../../../model/address.model";

@Component({
  moduleId: module.id,
  selector: 'cudOffice',
  templateUrl: 'cudOffice.component.html',
  styleUrls: ['cudOffice.component.css']
})
export class CudOfficeComponent extends GoogleMapsComponent implements OnInit {
  office : Office = <Office>{};
  cudOfficeForm: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private officeService: OfficeService,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
    super(mapsAPILoader, ngZone);
    this.office.address = <Address>{}
  }

  ngOnInit(): void {
    super.ngOnInit();
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
      floor: ['', [CustomValidators.min(-20), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(1000)]]
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
}
