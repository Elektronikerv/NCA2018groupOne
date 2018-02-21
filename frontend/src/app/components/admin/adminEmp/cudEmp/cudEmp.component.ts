import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {User} from "../../../../model/user.model";

@Component({
  moduleId: module.id,

  selector: 'cudEmp',
  templateUrl: 'cudEmp.component.html',
  styleUrls: ['cudEmp.component.css']
})

export class CudEmpComponent implements OnInit {
  addressOfficeRegisterByAdmin: FormGroup;
  cudEmployeeForm: FormGroup;

  // toasterService: ToasterService

  constructor(private router: Router, private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.cudEmployeeForm = this.formBuilder.group({
      email: new FormControl('', CustomValidators.email),
      password: new FormControl(CustomValidators.required),
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required),
      manager: new FormControl(CustomValidators.required),
      role: new FormControl(CustomValidators.required),
      name: new FormControl(CustomValidators.required),
      phoneNumber: new FormControl(CustomValidators.required),
      registrationDate: new FormControl(CustomValidators.required),
      address: this.initAddress()
    });
  }

  initAddress() {
    return this.addressOfficeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(0), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]]
    });
  }

  createEmployee(employee: User){
    console.log('employee: ' + employee.role);
  }

  validateField(field: string): boolean {
    console.log(this.cudEmployeeForm.get(field));
    return this.cudEmployeeForm.get(field).valid || !this.cudEmployeeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressOfficeRegisterByAdmin.get(field).valid || !this.addressOfficeRegisterByAdmin.get(field).dirty;
  }

// public config1 : ToasterConfig = new ToasterConfig({
//     positionClass: 'toast-top-center'
//   });

//   popToast() {
//     var toast: Toast = {
//       type: 'info',
//       title: 'Hello from Toast Title',
//       body: 'Hello from Toast Body'
//     };
//     this.toasterService.pop(toast);
//   }

}
