import {Component, OnInit} from "@angular/core";
import {Office} from "../../../../model/office.model";
import {OfficeService} from "../../../../service/office.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'editOffice',
  templateUrl: 'editOffice.component.html',
  styleUrls: ['editOffice.component.css']
})
export class EditOfficeComponent implements OnInit {
  office: Office;
  cudOfficeForm: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;

  constructor(private officeService: OfficeService,
              private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder) {

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

  initAddress() {
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
      })
  }

  validateField(field: string): boolean {
    return this.cudOfficeForm.get(field).valid || !this.cudOfficeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressOfficeRegisterByAdmin.get(field).valid || !this.addressOfficeRegisterByAdmin.get(field).dirty;
  }

}
