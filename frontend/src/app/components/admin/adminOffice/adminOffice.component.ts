import {Component, OnInit} from '@angular/core';
import {Office} from '../../../model/office.model';
import {OfficeService} from "../../../service/office.service";
import {Router} from "@angular/router";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import {CustomToastService} from "../../../service/customToast.service";


@Component({
  moduleId: module.id,
  selector: 'adminOffice',
  templateUrl: 'adminOffice.component.html',
  styleUrls: ['adminOffice.component.css']
})
export class AdminOfficeComponent implements OnInit {
  office: Office;
  offices: Office[] = [];
  sortedField = 'id';
  asc = false;
  page : number = 1;
  perPage: number = 15;

  constructor(private officeService: OfficeService,
              private router: Router,
              private toasterService: ToasterService,
              private customToastService: CustomToastService) {
  }

  ngOnInit(): void {
    this.getOfficesSortedBy();
    this.initCustomToast();
  }

  getOfficesSortedBy() {
    this.asc = !this.asc;
    this.officeService.getOfficesSortedBy(this.sortedField, this.asc)
          .subscribe((offices: Office[]) => this.offices = offices);
  }

  public config: ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center',
    animation: 'fade'
  });

  popToast(message: string) {
    let toast: Toast = {
      type: 'info',
      title: message,
      body: '',
      showCloseButton: true
    };
    this.toasterService.popAsync(toast);
  }

  initCustomToast(): void {
    if(this.customToastService.getMessage() != null){
      this.popToast(this.customToastService.getMessage());
      this.customToastService.setMessage(null);
    }
  }
}
