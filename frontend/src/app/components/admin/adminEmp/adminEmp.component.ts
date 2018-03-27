import {Component, OnInit} from '@angular/core';
import {User} from "../../../model/user.model";
import {EmployeeService} from "../../../service/emploee.service";
import {JwtHelper} from "angular2-jwt";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import {CustomToastService} from "../../../service/customToast.service";

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
  sortedField = 'firstName';
  asc = true;
  roles = [];
  showRolesFilter = false;
  page: number = 1;
  perPage: number = 20;

  constructor(private employeeService: EmployeeService,
              private toasterService: ToasterService,
              private customToastService: CustomToastService) {
  }

  ngOnInit(): void {
    let token = localStorage.getItem("currentUser");
    this.adminId = +this.jwtHelper.decodeToken(token).id;
    this.getEmployees();
  }


  getEmployees(): void {
    console.log('getEmployees()');
    this.employeeService.getEmployees().subscribe((employees: User[]) => {
      this.employees = employees;
      this.initCustomToast();
    });
  }

  getEmployeesSorted(): void {
    if (this.roles.length != 0) {
      this.getEmployeesFilteredAndSorted();
    } else {
      console.log('getEmployeesSortedBy(' + this.sortedField + ' asc = ' + this.asc + ')');
      this.employeeService.getEmployeesSorted(this.sortedField, this.asc)
        .subscribe((employees: User[]) => this.employees = employees);
    }
  }

  getEmployeesFilteredAndSorted(): void {
    if (this.roles.length == 0) {
      this.getEmployeesSorted();
    } else {
      console.log('getEmployeesFilteredAndSorted(' + this.sortedField + ' asc = ' + this.asc + 'roles=' + this.roles + ')');
      this.employeeService.getEmployeesFilteredAndSorted(this.sortedField, this.asc, this.roles)
        .subscribe((employees: User[]) => this.employees = employees);
    }
  }

  removeEmployee(employee: User): void {
    console.log('employee id: ' + employee.id);
    let id = employee.id;
    this.employees = this.employees.filter(h => h !== employee);
    this.employeeService.deleteEmployee(id).subscribe(()=>{
      this.popToast('User with id: ' + employee.id + ', deleted!')
    });
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

  public config: ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center',
    animation: 'fade'
  });

  popToast(message: string) {
    let toast1: Toast = {
      type: 'info',
      title: message,
      body: '',
      showCloseButton: true
    };
    this.toasterService.pop(toast1);
  }

  initCustomToast(): void {
    if(this.customToastService.getMessage() != null){
      this.popToast(this.customToastService.getMessage());
      this.customToastService.setMessage(null);
    }
  }

}
