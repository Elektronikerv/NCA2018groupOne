import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {User} from "../../../../model/user.model";
import {EmployeeService} from "../../../../service/emploee.service";
import {ROLES} from "../../../../mock-roles";
import {Role} from "../../../../model/role.model";
import {GoogleMapsComponent} from "../../../google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {Location} from "@angular/common";
import {FLAT_PATTERN, FLOOR_PATTERN, PHONE_PATTERN} from "../../../../model/utils";

@Component({
  moduleId: module.id,
  selector: 'cudEmp',
  templateUrl: 'cudEmp.component.html',
  styleUrls: ['cudEmp.component.css']
})
export class CudEmpComponent extends GoogleMapsComponent implements OnInit {
  addressOfficeRegisterByAdmin: FormGroup;
  cudEmployeeForm: FormGroup;
  user: User;
  Roles: Role[] = ROLES.filter(r => r.id !==7);
  checkedRoles: Role[] = [];

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private employeeService: EmployeeService,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
    super(mapsAPILoader, ngZone);
  }

  ngOnInit() {
    super.ngOnInit();
    this.cudEmployeeForm = this.formBuilder.group({
      email: new FormControl('', CustomValidators.email),
      password: new FormControl(CustomValidators.required),
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required),
      manager: new FormControl(CustomValidators.required),
      phoneNumber: [ CustomValidators.required,Validators.pattern(PHONE_PATTERN)],
      registrationDate: new FormControl({value: '', disabled: true}, CustomValidators.required),
      address: this.initAddress()
    });
  }

  initAddress() {
    return this.addressOfficeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  check(role: Role) {
    if (this.checkedRoles.includes(role)) {
      const index: number = this.checkedRoles.indexOf(role);
      if (index !== -1) {
        this.checkedRoles.splice(index, 1);
      }
    } else {
      this.checkedRoles.push(role);
    }
  }

  createEmployee(employee: User): void {
    // console.log('employee: ' + employee.roles[0].name);
    employee.roles = this.checkedRoles;
    console.log('employee: ' + JSON.stringify(employee));
    this.employeeService.createEmployee(employee).subscribe((employee: User) => {
      this.router.navigate(['admin/adminEmp']);
    })
  }

  validateField(field: string): boolean {
    // console.log(this.crudEmployeeForm.get(field));
    return this.cudEmployeeForm.get(field).valid || !this.cudEmployeeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressOfficeRegisterByAdmin.get(field).valid || !this.addressOfficeRegisterByAdmin.get(field).dirty;
  }

}
