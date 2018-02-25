import {Component, Input, OnInit} from "@angular/core";
import {User} from "../../../../model/User.model";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {EmployeeService} from "../../../../service/emploee.service";
import {Role} from "../../../../model/role.model";
import {ROLES} from "../../../../mock-roles";
import {Office} from "../../../../model/office.model";

@Component({
  selector: 'editEmployee',
  templateUrl: 'editEmployee.component.html',
  styleUrls: ['editEmployee.component.css']
})
export class EditEmployeeComponent implements OnInit {
  @Input() employee: User;
  cudEmployeeForm: FormGroup;
  addressEmployeeRegisterByAdmin: FormGroup;
  ROLES: Role[] = ROLES;
  rolesId: string[] = [];
  checkedRoles: Role[] = [];

  constructor(private employeeService: EmployeeService, private router: ActivatedRoute, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.getEmployee();
    this.cudEmployeeForm = this.formBuilder.group({
      email: new FormControl('', CustomValidators.email),
      password: new FormControl(CustomValidators.required),
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required),
      manager: new FormControl(CustomValidators.required),
      // name: new FormControl(CustomValidators.required),
      phoneNumber: new FormControl(CustomValidators.required),
      // registrationDate: new FormControl(CustomValidators.required),
      address: this.initAddress()
    });
  }


  initAddress() {
    return this.addressEmployeeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(0), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(200)]]
    });
  }

  initRoles() {
    console.log('initRoles: ' + JSON.stringify(this.employee.roles));
    // this.employee.roles.forEach((role: Role) => {
    //   this.rolesId.push(role.name);
    // });
    this.ROLES.forEach((role: Role) => {
      console.log('initRoles: ' + JSON.stringify(this.rolesId));
      if (this.employee.roles.includes(role.name)) {
        role.checked = true;
        this.checkedRoles.push(role);
        // console.log('initRoles: ' + JSON.stringify(role));
      }
    })
  }

  check(role: Role) {
    console.log('check: ' + JSON.stringify(this.checkedRoles));
    if (this.checkedRoles.includes(role)) {
      const index: number = this.checkedRoles.indexOf(role);
      if (index !== -1) {
        this.checkedRoles.splice(index, 1);
      }
    } else {
      this.checkedRoles.push(role);
    }
  }

  getEmployee() {
    const id = +this.router.snapshot.paramMap.get('id');
    this.employeeService.getEmployeeById(id).subscribe(employee => {
      this.employee = employee;
      this.initRoles();
    });
  }

  save(): void {
    this.employee.roles = this.checkedRoles;
    console.log('employee.roles: ' + JSON.stringify(this.checkedRoles));
    console.log('employee.roles: ' + JSON.stringify(this.employee.roles));
    this.employeeService.update(this.employee)
      .subscribe((Employee: User) => this.employee = Employee);
  }

  validateField(field: string): boolean {
    return this.cudEmployeeForm.get(field).valid || !this.cudEmployeeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressEmployeeRegisterByAdmin.get(field).valid || !this.addressEmployeeRegisterByAdmin.get(field).dirty;
  }

}
