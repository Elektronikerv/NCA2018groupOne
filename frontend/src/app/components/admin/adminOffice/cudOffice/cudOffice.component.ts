import { Component , OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";

// import {User} from "../../model/user.model";
// import {UserService} from "../../service/user.service";

@Component ({
    moduleId: module.id,

    selector: 'cudOffice',
    templateUrl: 'cudOffice.component.html',
    styleUrls: ['cudOffice.component.css']
}) 

export class CudOfficeComponent 
implements OnInit{
    cudOfficeForm: FormGroup;
//     // formBuilder: FormBuilder;
    constructor(private router: Router, private formBuilder: FormBuilder
        // , private toasterService: ToasterService
    ) {

    }

    ngOnInit() {
        this.cudOfficeForm = this.formBuilder.group({
          name: new FormControl(CustomValidators.required),
          street: new FormControl(CustomValidators.required, Validators.maxLength(256)),
          house: ['', [Validators.required]],
          floor: ['',  CustomValidators.number],
          flat:  new FormControl(CustomValidators.number)
        });

      }

      submitForm():void{

    };

      validateField(field: string): boolean {
        console.log(this.cudOfficeForm.get(field));
        return this.cudOfficeForm.get(field).valid || !this.cudOfficeForm.get(field).dirty;
      }

}