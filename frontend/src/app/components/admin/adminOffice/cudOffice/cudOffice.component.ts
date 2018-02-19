import { Component } from '@angular/core';
import {AdminService} from "../../../../service/admin.service";
import {Office} from "../../../../model/office.model";
import {Router} from "@angular/router";
import {FormGroup} from "@angular/forms";

@Component ({
    moduleId: module.id,

    selector: 'cudOffice',
    templateUrl: 'cudOffice.component.html',
    styleUrls: ['cudOffice.component.css']
})

export class CudOfficeComponent {
  officeRegisterByAdmin: FormGroup;
  addressOfficeRegisterByAdmin: FormGroup;

  constructor(private adminService: AdminService, private router: Router){}

  createOffice(office: Office):void{
    console.log('createOffice(office: Office) office: ' + office);
    this.adminService.createOffice(office).subscribe((office: Office)=>{
      this.router.navigate(['/adminOffice']);
    })

  }

}
