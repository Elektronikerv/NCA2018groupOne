import { Component , OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";

@Component ({
    moduleId: module.id,

    selector: 'cudEmp',
    templateUrl: 'cudEmp.component.html',
    styleUrls: ['cudEmp.component.css']
})

export class CudEmpComponent  implements OnInit{

    cudEmployeeForm: FormGroup;

    // toasterService: ToasterService

    constructor(private router: Router, private formBuilder: FormBuilder) {

    }

    ngOnInit(){
        this.cudEmployeeForm = this.formBuilder.group({
          email:  new FormControl('', CustomValidators.email),
          password:  new FormControl(CustomValidators.required),
          firstName:  new FormControl(CustomValidators.required),
          lastName:  new FormControl(CustomValidators.required),
          manager:  new FormControl(CustomValidators.required),
          position:  new FormControl(CustomValidators.required),
          name: new FormControl(CustomValidators.required),
          phoneNumber: new FormControl(CustomValidators.required),
          registrationDate: new FormControl(CustomValidators.required),
          street: new FormControl(CustomValidators.required, Validators.maxLength(256)),
          house: ['', [Validators.required]],
          floor: new FormControl('', CustomValidators.digits),
          flat: new FormControl('', CustomValidators.digits)
        });
      }

      validateField(field: string): boolean {
        console.log(this.cudEmployeeForm.get(field));
        return this.cudEmployeeForm.get(field).valid || !this.cudEmployeeForm.get(field).dirty;
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

submitForm():void{

};

}