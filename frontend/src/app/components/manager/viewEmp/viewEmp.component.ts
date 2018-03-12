import {EmployeeService} from "../../../service/emploee.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {User} from "../../../model/user.model";
import {ManagerService} from "../../../service/manager.service";

@Component({
  selector: 'viewEmp',
  templateUrl: 'viewEmp.component.html',
  styleUrls: ['viewEmp.component.css']
})
export class ViewEmployeeComponent implements OnInit {
  employee: User;
  ccagentOrders: number;
  courierOrders: number;
  isCourier: boolean;
  isCCAgent: boolean;

  constructor(private employeeService: EmployeeService,
              private managerService: ManagerService,
              private route: Router,
              private router: ActivatedRoute) {
    this.isCourier = false;
    this.isCCAgent = false;
  }

  ngOnInit(): void {
    this.getEmployee();
    console.log(this.employee);
  }

  getEmployee() {
    console.log('getEmployee');
    const id = +this.router.snapshot.paramMap.get('id');
    this.employeeService.getEmployeeById(id).subscribe(employee => {
      this.employee = employee;
      this.getStatisticOrders();
    });
    console.log(this.employee);
  }

  getStatisticOrders() {
    console.log('getStatisticOrders()');
    console.log((this.employee.roles.toString().includes('CALL_CENTER_AGENT')));
    if (this.employee.roles.includes('CALL_CENTER_AGENT')) {
      this.isCCAgent = true;
      this.managerService.getCountOrdersByCCAgentInCurrentMonth(this.employee.id)
        .subscribe(result => this.ccagentOrders = result);
      console.log(this.ccagentOrders);
    }
    if (this.employee.roles.includes('COURIER')) {
      this.isCourier = true;
      this.managerService.getCountOrdersByCourierInCurrentMonth(this.employee.id)
        .subscribe(result => this.courierOrders = result);
      console.log(this.courierOrders);
    }
  }
}
