import {Component, OnInit} from '@angular/core';
import {Office} from '../../../model/office.model';
import {OfficeService} from "../../../service/office.service";


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

  constructor(private officeService: OfficeService) {
  }

  ngOnInit(): void {
    this.getOfficesSortedBy();
  }

  getOfficesSortedBy() {
    this.asc = !this.asc;
    this.officeService.getOfficesSortedBy(this.sortedField, this.asc)
          .subscribe((offices: Office[]) => this.offices = offices);
  }

  removeOffice(office: Office): void {
    console.log('office id: ' + office.id);
    let id = office.id;
    this.offices = this.offices.filter(h => h !== office);
    this.officeService.deleteOffice(id).subscribe();
  }
}
