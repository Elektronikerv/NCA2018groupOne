import {Component, ElementRef, Input, NgZone, OnInit, ViewChild} from "@angular/core";
import {User} from "../../../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {EmployeeService} from "../../../../service/emploee.service";
import {Role} from "../../../../model/role.model";
import {ROLES} from "../../../../mock-roles";
import {GoogleMapsComponent} from "../../../utils/google-maps/google-maps.component";
import {MapsAPILoader} from "@agm/core";
import {JwtHelper} from "angular2-jwt";
import {FLAT_PATTERN, FLOOR_PATTERN, PHONE_PATTERN} from "../../../../model/utils";
import {ManagerService} from "../../../../service/manager.service";
import {CustomToastService} from "../../../../service/customToast.service";

@Component({
  selector: 'editEmployee',
  templateUrl: 'editEmployee.component.html',
  styleUrls: ['editEmployee.component.css']
})
export class EditEmployeeComponent implements OnInit {
  @Input() employee: User;
  private jwtHelper: JwtHelper = new JwtHelper();
  adminId: number;
  cudEmployeeForm: FormGroup;
  addressEmployeeRegisterByAdmin: FormGroup;
  Roles: Role[] = ROLES.filter(r => r.id <= 6);
  checkedRoles: string[] = [];
  managers: User[] = [];
  map: GoogleMapsComponent;
  currentManager: User;

  @ViewChild('searchAddress')
  public searchAddressRef: ElementRef;

  constructor(private employeeService: EmployeeService,
              private route: Router,
              private router: ActivatedRoute,
              private formBuilder: FormBuilder,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone,
              private managerService: ManagerService,
              private customToastService: CustomToastService) {
    this.map = new GoogleMapsComponent(mapsAPILoader, ngZone);
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.map.setSearchElement(this.searchAddressRef);
    }, 700);
    this.map.ngOnInit();
    this.initCurrentUserId();
    this.getEmployee();
    this.getManager();
    this.getManagers();
    this.cudEmployeeForm = this.formBuilder.group({
      email: new FormControl('', CustomValidators.email),
      firstName: new FormControl(CustomValidators.required, [Validators.maxLength(45), Validators.minLength(3)]),
      lastName: new FormControl(CustomValidators.required, [Validators.maxLength(45), Validators.minLength(3)]),
      manager: new FormControl(CustomValidators.required),
      phoneNumber: [CustomValidators.required, Validators.pattern(PHONE_PATTERN)],
      address: this.initAddress()

    });
  }

  updateStreet() {
    this.employee.address.street = this.map.street;
  }

  updateHouse() {
    this.employee.address.house = this.map.house;
  }

  updateStreetHouse(){
    setTimeout(()=>{
      this.employee.address.house = this.map.house;
      this.employee.address.street = this.map.street;
    }, 500);
  }

  initCurrentUserId() {
    let token = localStorage.getItem("currentUser");
    this.adminId = +this.jwtHelper.decodeToken(token).id;
  }

  isClient(role: Role){
    return role.id === 6;
  }

  isEditHimself(role : Role): boolean {
    return this.adminId === this.employee.id && (role.id === 1 || role.id === 6);
  }

  initCheckRoles(){
    this.Roles.forEach(r => r.checked = false);
  }

  mapReady($event,yourLocation) {
    this.map.mapReady($event,yourLocation);
    this.map.geocodeAddress(this.employee.address.street, this.employee.address.house);
  }

  getManagers(): void {
    this.managerService.getManagers().subscribe((managers: User[]) => {
      this.managers = managers
    })
  }

  compareManager(manager1: User, manager2: User) {
    return manager1.id == manager2.id;
  }

  getManager(): void {
    const id = +this.router.snapshot.paramMap.get('id');
    console.log("getManager(employeeId): " + JSON.stringify(id));
    this.managerService.getManager(id).subscribe((manager: User) => {
      this.currentManager = manager;
      // this.mgr = manager.id;
      // console.log("mrg: " + this.mgr)
    })
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
        this.checkedRoles.push(role.name);
        // console.log('initRoles: ' + JSON.stringify(role));
      }
    })
  }

  check(role: Role) {
    console.log('check: ' + JSON.stringify(this.checkedRoles));
    if (this.checkedRoles.includes(role.name)) {
      const index: number = this.checkedRoles.indexOf(role.name);
      if (index !== -1) {
        this.checkedRoles.splice(index, 1);
      }
    } else {
      this.checkedRoles.push(role.name);
    }
  }

  getEmployee() {
    const id = +this.router.snapshot.paramMap.get('id');
    this.employeeService.getEmployeeById(id).subscribe(employee => {
      this.employee = employee;
      console.log('id: ' + employee.id);
      this.initRoles();
    });
  }

  save(employee: User): void {
    employee.id = this.employee.id;
    employee.address = this.employee.address;
    // employee.managerId = this.currentManager;
    console.log('employee: ' + JSON.stringify(employee));
    employee.roles = this.checkedRoles;
    // console.log('employee.roles: ' + JSON.stringify(this.checkedRoles));
    // console.log('employee.roles: ' + JSON.stringify(this.employee.roles));
    this.employeeService.update(employee).subscribe((employee: User) => {
      this.employee = employee;
      this.customToastService.setMessage('Employee ' + employee.lastName + ' is updated');
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
