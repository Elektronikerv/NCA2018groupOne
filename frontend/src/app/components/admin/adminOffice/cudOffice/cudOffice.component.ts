import {Component, OnInit} from '@angular/core';
import {Office} from "../../../../model/office.model";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {OfficeService} from "../../../../service/office.service";

@Component({
  moduleId: module.id,
  selector: 'cudOffice',
  templateUrl: 'cudOffice.component.html',
  styleUrls: ['cudOffice.component.css']
})
export class CudOfficeComponent implements OnInit {
  cudOfficeForm: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private officeService: OfficeService) {
  }

  ngOnInit(): void {
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
      floor: ['', [CustomValidators.min(0), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]]
    });
  }

  createOffice(office: Office): void {
    console.log('createOffice(office: Office) office: ' + office.name);
    this.officeService.createOffice(office).subscribe((office: Office) => {
      this.router.navigate(['admin/adminOffice']);
    })
  }

  validateField(field: string): boolean {
    return this.cudOfficeForm.get(field).valid || !this.cudOfficeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressOfficeRegisterByAdmin.get(field).valid || !this.addressOfficeRegisterByAdmin.get(field).dirty;
  }
}


