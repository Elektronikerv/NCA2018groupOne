import {Component, NgZone, OnInit} from '@angular/core';
import {Office} from '../../../../model/office.model';
import {OfficeService} from '../../../../service/office.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from 'ng2-validation';
import {GoogleMapsComponent} from '../../../google-maps/google-maps.component';
import {MapsAPILoader} from '@agm/core';

@Component({
  selector: 'editOffice',
  templateUrl: 'editOffice.component.html',
  styleUrls: ['editOffice.component.css']
})
export class EditOfficeComponent extends GoogleMapsComponent implements OnInit {
  office: Office;
  cudOfficeForm: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;

  constructor(private officeService: OfficeService,
              private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
    super(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    this.getOffice();
    this.cudOfficeForm = this.formBuilder.group({
        name: ['', [Validators.required, Validators.minLength(5)]],
        address: this.initAddress(),
        description: [''],
      }
    );
  }
  fillStreetAndHouse(newAddress : string){
    this.inputAddress = newAddress;
    this.office.address.street = this.inputAddress.split(',')[0].trim();
    this.office.address.house = this.inputAddress.split(',')[1].trim();
  }

  mapReady($event) {
    $event.controls[google.maps.ControlPosition.RIGHT_CENTER].push(document.getElementById('your_location'));
    $event.controls[google.maps.ControlPosition.TOP_CENTER].push(document.getElementById('inputSearch'));
    this.geocodeAddress(this.office.address.street, this.office.address.house);
  }

  initAddress() {
    super.ngOnInit();
    return this.addressOfficeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(0), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]]
    });
  }

  getOffice() {
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    console.log('getOffice() id: ' + id);
    this.officeService.getOfficeById(id).subscribe((office: Office) => this.office = office);
  }

  save(): void {
    console.log('save() office: ' + this.office.name);
    this.officeService.update(this.office)
      .subscribe((office: Office) => {
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
