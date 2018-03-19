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
  currents: EmpProfile[];
  private managerId: number;
  sortedField = 'id';
  asc = true;
  page : number = 1;
  perPage: number = 20;
  state: string = 'all';
  filteringValue;

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
    this.managerService.getEmployees(this.managerId).subscribe((employees: EmpProfile[]) => {
      this.employees = employees;
      this.currents = employees;
    })
  }

  getWorkingNow(): void {
    this.state = 'working_now';
    this.currents = this.currents.filter(x => x.workingNow);
    console.log(this.currents);
  }

  getAll(): void {
    this.state = 'all';
    this.currents = this.employees;
  }

  filter(selected: string) {
    if (selected.trim()) {
      console.log(selected);
      this.managerService.getEmployeesByLastName(this.managerId, selected)
        .subscribe(data => {
          this.employees = data;
          this.currents = data;
          if (this.state == 'working_now') {
            this.getWorkingNow();
          }
        });
    } else {
      this.updateListEmp();
    }
  }

  updateListEmp() {
    this.getEmployees();
    if (this.state == 'working_now') {
      this.getWorkingNow();
    }
  }
}
