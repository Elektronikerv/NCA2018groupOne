import {Component, ElementRef, Input, NgZone, OnInit, ViewChild} from "@angular/core";
import {User} from "../../../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {EmployeeService} from "../../../../service/emploee.service";
import {Role} from "../../../../model/role.model";
import {ROLES} from "../../../../mock-roles";
import {GoogleMapsComponent} from "../../../google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";

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
  map: GoogleMapsComponent;

  @ViewChild('searchAddress')
  public searchAddressRef: ElementRef;

  constructor(private employeeService: EmployeeService,
              private route: Router,
              private router: ActivatedRoute,
              private formBuilder: FormBuilder,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone) {
    this.map = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.map.setSearchElement(this.searchAddressRef);
    }, 700);
    this.map.ngOnInit();
    this.getEmployee();
    this.cudEmployeeForm = this.formBuilder.group({
      email: new FormControl('', CustomValidators.email),
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required),
      manager: new FormControl(CustomValidators.number),
      phoneNumber: new FormControl(CustomValidators.required),
      address: this.initAddress()
    });
  }

  updateStreetHouse() {
    setTimeout(() => {
      this.employee.address.street = this.map.street;
      this.employee.address.house = this.map.house;
    }, 500);
  }

  mapReady($event,yourLocation, inputSearch) {
    this.map.mapReady($event,yourLocation,inputSearch);
    this.map.geocodeAddress(this.employee.address.street, this.employee.address.house);
  }

  initAddress() {
    return this.addressEmployeeRegisterByAdmin = this.formBuilder.group({
      street: ['', [Validators.required, Validators.minLength(5)]],
      house: ['', [Validators.required, Validators.maxLength(5)]],
      floor: ['', [CustomValidators.min(-20), CustomValidators.max(200)]],
      flat: ['', [CustomValidators.min(0), CustomValidators.max(1000)]]
    });
  }

  initRoles() {
    console.log('initRoles: ' + JSON.stringify(this.employee.roles));
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
