import {Component, OnInit} from '@angular/core';
import {ManagerService} from "../../service/manager.service";
import {AuthService} from "../../service/auth.service";
import {EmpProfile} from "../../model/empProfile.model";


@Component({
  moduleId: module.id,
  selector: 'managerEmp',
  templateUrl: 'managerEmp.component.html',
  styleUrls: ['managerEmp.component.css']
})

export class ManagerEmpComponent implements OnInit {
  employees: EmpProfile[];
  private managerId: number;
  sortedField = 'id'
  asc = true;

  constructor(private managerService: ManagerService, private authService: AuthService) {
  }

  ngOnInit(): void {
    console.log('id - ' + this.managerId);
    this.managerId = this.authService.currentUserId();
    console.log('id - ' + this.managerId);
    this.getEmployees();
  }

  getEmployees(): void {
    console.log('id - ' + this.managerId);
    console.log('getEmployees()');
    console.log(this.managerId);
    this.managerService.getEmployees(this.managerId).subscribe((employees: EmpProfile[]) => this.employees = employees);

  }

}
