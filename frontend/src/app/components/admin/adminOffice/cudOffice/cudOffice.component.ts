import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../../../service/admin.service";
import {Office} from "../../../../model/office.model";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";

@Component ({
    moduleId: module.id,

    selector: 'cudOffice',
    templateUrl: 'cudOffice.component.html',
    styleUrls: ['cudOffice.component.css']
})

export class CudOfficeComponent implements OnInit{
  officeRegisterByAdmin: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;

  constructor(private adminService: AdminService, private router: Router, private formBuilder: FormBuilder){}

  ngOnInit(): void{
    this.officeRegisterByAdmin = this.formBuilder.group({
      name:['',[Validators.required, Validators.minLength(5)]],
      address: this.initAddress(),
      description:[''],
      }
    );
  }

  initAddress(){
    return this.addressOfficeRegisterByAdmin = this.formBuilder.group({
      street:  new FormControl([[CustomValidators.required]]),
      house:  ['',[CustomValidators.required, Validators.maxLength(5)]],
      floor:  new FormControl(['', [CustomValidators.required]]),
      flat:  new FormControl([[CustomValidators.required]])
    });

  }

  createOffice(office: Office):void{
    console.log('createOffice(office: Office) office: ' + office.name);
    this.adminService.createOffice(office).subscribe((office: Office)=>{
      this.router.navigate(['/adminOffice']);
    })
  }
}
