import {Component, OnInit} from '@angular/core';
import {User} from "../../../model/user.model";
import {EmployeeService} from "../../../service/emploee.service";
import {JwtHelper} from "angular2-jwt";

@Component({
  moduleId: module.id,
  selector: 'adminEmp',
  templateUrl: 'adminEmp.component.html',
  styleUrls: ['adminEmp.component.css']
})

export class AdminEmpComponent implements OnInit {
  adminId: number;
  private jwtHelper: JwtHelper = new JwtHelper();
  employees: User[];
  sortedField = '';
  asc: boolean;
  roles = [];
  showRolesFilter = false;
  page: number = 1;
  perPage: number = 20;

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    let token = localStorage.getItem("currentUser");
    this.adminId = +this.jwtHelper.decodeToken(token).id;
    this.getEmployees();
  }

  getEmployees(): void {
    console.log('getEmployees()');
    this.employeeService.getEmployees().subscribe((employees: User[]) => this.employees = employees);
  }

  getEmployeesSortedBy(): void {
    if (this.roles.length != 0) {
      this.getEmployeesSortedAndFilterBy();
    } else {
      console.log('getEmployeesSortedBy(' + this.sortedField + ' asc = ' + this.asc + ')');
      this.employeeService.getEmployeesSortedBy(this.sortedField, this.asc)
        .subscribe((employees: User[]) => this.employees = employees);
    }
  }

  getEmployeesSortedAndFilterBy(): void {
    if (this.roles.length == 0) {
      this.getEmployeesSortedBy();
    } else {
      console.log('getEmployeesSortedAndFilterBy(' + this.sortedField + ' asc = ' + this.asc + 'roles=' + this.roles + ')');
      this.employeeService.getEmployeesSortedAndFilterBy(this.sortedField, this.asc, this.roles)
        .subscribe((employees: User[]) => this.employees = employees);
    }
  }

  removeEmployee(employee: User): void {
    console.log('employee id: ' + employee.id);
    let id = employee.id;
    this.employees = this.employees.filter(h => h !== employee);
    this.employeeService.deleteEmployee(id).subscribe();
  }

  addRoleToFilter(role) {
    this.roles.push(role);
  }

  deleteRoleFromFilter(role) {
    this.roles.splice(this.roles.indexOf(role), 1);
  };

  filter(selected: string) {
    if (selected) {
      this.employeeService.searchEmployees(selected).subscribe(data => this.employees = data);
    } else {
      this.employeeService.getEmployees().subscribe(data => this.employees = data);
    }
  }
}
