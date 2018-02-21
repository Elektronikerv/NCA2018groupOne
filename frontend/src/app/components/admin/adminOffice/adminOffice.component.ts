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

  constructor(private officeService: OfficeService) {
  }

  ngOnInit(): void {
    this.getOffices();
  }

  getOffices(): void {
    console.log('getOffices()');
    this.officeService.getOffices().subscribe((offices: Office[]) => this.offices = offices)
  }

  removeOffice(office: Office): void {
    console.log('office id: ' + office.id);
    let id = office.id;
    this.offices = this.offices.filter(h => h !== office);
    this.officeService.deleteOffice(id).subscribe();
  }
}
