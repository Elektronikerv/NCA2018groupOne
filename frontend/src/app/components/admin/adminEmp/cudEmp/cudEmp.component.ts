import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {User} from "../../../../model/user.model";
import {EmployeeService} from "../../../../service/emploee.service";
import {ROLES} from "../../../../mock-roles";
import {Role} from "../../../../model/role.model";
import {GoogleMapsComponent} from "../../../utils/google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {FLAT_PATTERN, FLOOR_PATTERN, PHONE_PATTERN} from "../../../../model/utils";
import {ManagerService} from "../../../../service/manager.service";
import {PasswordService} from "../../../../service/password.service";
import {CustomToastService} from "../../../../service/customToast.service";

@Component({
  moduleId: module.id,
  selector: 'cudEmp',
  templateUrl: 'cudEmp.component.html',
  styleUrls: ['cudEmp.component.css']
})
export class CudEmpComponent implements OnInit {
  addressOfficeRegisterByAdmin: FormGroup;
  cudEmployeeForm: FormGroup;
  user: User;
  Roles: Role[] = ROLES.filter(r => r.id < 6);
  checkedRoles: string[] = [];
  managers: User[] = [];
  manager: User;
  map: GoogleMapsComponent;

  @ViewChild('searchAddress')
  public searchAddressRef: ElementRef;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private employeeService: EmployeeService,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone,
              private managerService: ManagerService,
              private passwordService: PasswordService,
              private customToastService: CustomToastService) {
    this.map = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit() {
    this.map.setSearchElement(this.searchAddressRef);
    this.map.ngOnInit();
    this.getManagers();
    this.initRoles();
    this.cudEmployeeForm = this.formBuilder.group({
        email: new FormControl('', CustomValidators.email),
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
        firstName: new FormControl(CustomValidators.required, [Validators.maxLength(45), Validators.minLength(3)]),
        lastName: new FormControl(CustomValidators.required, [Validators.maxLength(45), Validators.minLength(3)]),
        manager: new FormControl(CustomValidators.required),
        phoneNumber: [CustomValidators.required, Validators.pattern(PHONE_PATTERN)],
        address: this.initAddress()
      }
      , {validator: this.passwordService.passwordMatching('password', 'confirmPassword')}
    );
  }

  initAddress() {
    return this.addressOfficeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  getManagers(): void {
    this.managerService.getManagers().subscribe((managers: User[]) => {
      this.managers = managers
    })
  }

  initRoles(): void {
    this.checkedRoles.push(ROLES[5].name);
  }

  check(role: Role) {
    if (this.checkedRoles.includes(role.name)) {
      const index: number = this.checkedRoles.indexOf(role.name);
      if (index !== -1) {
        this.checkedRoles.splice(index, 1);
      }
    } else {
      this.checkedRoles.push(role.name);
    }
  }

  createEmployee(employee: User): void {
    employee.managerId = this.manager.id;
    // console.log('employee: ' + employee.roles[0].name);
    employee.roles = this.checkedRoles;
    console.log('employee: ' + JSON.stringify(employee));
    this.employeeService.createEmployee(employee).subscribe((employee: User) => {
      this.customToastService.setMessage('Employee created!');
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
