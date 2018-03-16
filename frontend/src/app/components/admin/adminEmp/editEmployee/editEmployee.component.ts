import {Component, Input, NgZone, OnInit} from "@angular/core";
import {User} from "../../../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {EmployeeService} from "../../../../service/emploee.service";
import {Role} from "../../../../model/role.model";
import {ROLES} from "../../../../mock-roles";
import {GoogleMapsComponent} from "../../../google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {JwtHelper} from "angular2-jwt";
import {FLAT_PATTERN, FLOOR_PATTERN, PHONE_PATTERN} from "../../../../model/utils";

@Component({
  selector: 'editEmployee',
  templateUrl: 'editEmployee.component.html',
  styleUrls: ['editEmployee.component.css']
})
export class EditEmployeeComponent extends GoogleMapsComponent implements OnInit {
  employee: User;
  private jwtHelper: JwtHelper = new JwtHelper();
  adminId: number;
  cudEmployeeForm: FormGroup;
  addressEmployeeRegisterByAdmin: FormGroup;
  Roles: Role[] = ROLES.filter(r => r.id !==7);
  checkedRoles: Role[] = [];

  constructor(private employeeService: EmployeeService,
              private route: Router,
              private router: ActivatedRoute,
              private formBuilder: FormBuilder,
              public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
    super(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    super.ngOnInit();
    this.initCurrentUserId();
    this.getEmployee();


    this.cudEmployeeForm = this.formBuilder.group({
      email: new FormControl('', CustomValidators.email),
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required),
      manager: new FormControl(CustomValidators.number),
      phoneNumber: [ CustomValidators.required,Validators.pattern(PHONE_PATTERN)],
      address: this.initAddress()
    });
  }

  initCurrentUserId() {
    let token = localStorage.getItem("currentUser");
    this.adminId = +this.jwtHelper.decodeToken(token).id;
  }

  isEditHimself(role : Role): boolean{
    return (this.adminId === this.employee.id && role.id ===1)
  }

  initCheckRoles(){
    // if (this.adminId === this.employee.id) {
    //   let adminRole : Role = this.Roles[1];
    //   adminRole.checked = true;
    //   this.checkedRoles.push(adminRole);
    //   this.Roles = this.Roles.filter(item => item.id !== 1);
    // }
    this.Roles.forEach(r => r.checked = false);
  }

  fillStreetAndHouse(newAddress: string) {
    this.inputAddress = newAddress;
    this.employee.address.street = this.inputAddress.split(',')[0].trim();
    this.employee.address.house = this.inputAddress.split(',')[1].trim();
  }

  mapReady($event) {
    super.mapReady($event);
    this.geocodeAddress(this.employee.address.street, this.employee.address.house);
  }

  initAddress() {
    return this.addressEmployeeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: [Validators.required, Validators.pattern(FLOOR_PATTERN)],
      flat: [Validators.required, Validators.pattern(FLAT_PATTERN)]
    });
  }

  initRoles() {
    this.initCheckRoles();
    // console.log('initRoles: ' + JSON.stringify(this.employee.roles));
    this.Roles.forEach((role: Role) => {
      // console.log('initRoles: ' + JSON.stringify(this.rolesId));
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
    console.log('employee.pass: ' + this.employee.password);
    this.employeeService.update(this.employee).subscribe((employee: User) => {
      this.employee = employee;
      this.route.navigate(['admin/adminEmp']);
    })
  }

  validateField(field: string): boolean {
    return this.cudEmployeeForm.get(field).valid || !this.cudEmployeeForm.get(field).dirty;
  }

  validateFieldAddress(field: string): boolean {
    return this.addressEmployeeRegisterByAdmin.get(field).valid || !this.addressEmployeeRegisterByAdmin.get(field).dirty;
  }
}
