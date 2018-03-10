import {Component, OnInit} from '@angular/core';
import {User} from "../../../model/user.model";
import {EmployeeService} from "../../../service/emploee.service";

@Component({
  moduleId: module.id,

  selector: 'adminEmp',
  templateUrl: 'adminEmp.component.html',
  styleUrls: ['adminEmp.component.css']
})

export class AdminEmpComponent implements OnInit {
  employees: User[];
  sortedField = '';
  asc = true;
  adminFilter = false;
  managerFilter = false;
  ccAgentFilter = false;
  courierFilter = false;

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(): void {
    console.log('getEmployees()');
    this.employeeService.getEmployees().subscribe((employees: User[]) => this.employees = employees);
  }

  removeEmployee(employee: User): void {
    console.log('employee id: ' + employee.id);
    let id = employee.id;
    this.employees = this.employees.filter(h => h !== employee);
    this.employeeService.deleteEmployee(id).subscribe();
  }
}
