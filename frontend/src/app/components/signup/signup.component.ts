import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";


@Component({
    moduleId: module.id,
    selector: 'signup',
    templateUrl:'signup.component.html',
    styleUrls: ['signup.component.css']
    })
export class SignupComponent implements OnInit{
  userRegisterForm: FormGroup;
  errorMs: any;

  constructor(private userService: UserService, private router: Router, private formBuilder: FormBuilder, private toasterService: ToasterService) {}

  ngOnInit() {
    this.userRegisterForm = this.formBuilder.group({
      firstName: new FormControl(CustomValidators.required),
      lastName: new FormControl(CustomValidators.required, Validators.maxLength(256)),
      email: ['', [Validators.required, CustomValidators.email]],
      phoneNumber: ['', CustomValidators.phone('UKR')],
      password:  new FormControl(CustomValidators.required)
    });
    this.errorMs = '';
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
    this.userService.create(user).subscribe(
      errors => {
        if(!Array.isArray(errors)) {
          this.router.navigate(['/landing']);
        }else{
          this.errorMs = errors[0];
        }
      })
  }

  validateField(field: string): boolean {
    console.log(this.userRegisterForm.get(field));
    return this.userRegisterForm.get(field).valid || !this.userRegisterForm.get(field).dirty;
  }

  validateEmailOnExisting(): boolean{
    return this.errorMs == '';
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
