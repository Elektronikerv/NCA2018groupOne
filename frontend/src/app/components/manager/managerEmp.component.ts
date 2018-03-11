import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user.model";
import {ManagerService} from "../../service/manager.service";
import {AuthService} from "../../service/auth.service";


@Component({
  moduleId: module.id,
  selector: 'managerEmp',
  templateUrl: 'managerEmp.component.html',
  styleUrls: ['managerEmp.component.css']
})

export class ManagerEmpComponent implements OnInit {
  employees: User[];
  private managerId: number;

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
    this.managerService.getEmployees(this.managerId).subscribe((employees: User[]) => this.employees = employees);

  }

}
