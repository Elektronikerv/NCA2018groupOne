import {Component, OnInit} from '@angular/core';
import { Office } from '../../../model/office.model';
import { Address } from '../../../model/address.model';
import {AdminService} from "../../../service/admin.service";


@Component ({
    moduleId: module.id,

    selector: 'adminOffice',
    templateUrl: 'adminOffice.component.html',
    styleUrls: ['adminOffice.component.css']
})

export class AdminOfficeComponent implements OnInit{
  office: Office;
  offices: Office[] = [];

  constructor(private adminService: AdminService){}

  ngOnInit(): void {
    this.getOffices();
  }

  getOffices(): void{
    console.log('getOffices()');
    this.adminService.getOffices().subscribe((offices:Office[]) => this.offices = offices)
  }



}
