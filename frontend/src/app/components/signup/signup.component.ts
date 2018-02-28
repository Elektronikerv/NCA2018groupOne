import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import {PasswordService} from "../../service/password.service";


@Component({
    moduleId: module.id,
    selector: 'signup',
    templateUrl:'signup.component.html',
    styleUrls: ['signup.component.css']
    })
export class SignupComponent implements OnInit{
  userRegisterForm: FormGroup;

  constructor(private userService: UserService,
              private router: Router,
              private formBuilder: FormBuilder,
              private toasterService: ToasterService,
              private passwordService: PasswordService
  ) {}

  ngOnInit() {
    this.userRegisterForm = this.formBuilder.group({
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required, Validators.maxLength(256)),
      email: ['', [Validators.required, CustomValidators.email]],
      phoneNumber: ['', CustomValidators.phone('UKR')],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
    }, {validator: this.passwordService.passwordConfirming});

  }

  public config1 : ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center'
  });

  popToast() {
    var toast: Toast = {
      type: 'info',
      title: 'Hello from Toast Title',
      body: 'Hello from Toast Body'
    };
    this.toasterService.pop(toast);
  }

  submitForm(user: User):void{
    console.log(user);
    this.userService.create(user).subscribe((user: User) => {
      this.popToast();
      this.router.navigate(['/landing']);
    });
  }

  validateField(field: string): boolean {
    console.log(this.userRegisterForm.get(field));
    return this.userRegisterForm.get(field).valid || !this.userRegisterForm.get(field).dirty;
  }

  checkPass(): boolean{
    return this.userRegisterForm.get(['password']).value != this.userRegisterForm.get(['confirmPassword']).value && this.userRegisterForm.get(['confirmPassword']).value != null;
  }

  // private initForm(): void {
  //   this.userRegisterForm = new FormGroup({
  //     firstName: new FormControl(),
  //     lastName:  new FormControl(),
  //     email:  new FormControl(),
  //     phoneNumber: new FormControl(),
  //     password: new FormControl(),
  //     certainPassword: new FormControl()
  //   });
  // }

}
