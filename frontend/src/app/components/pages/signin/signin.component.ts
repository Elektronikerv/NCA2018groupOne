import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import {AuthService} from "../../../service/auth.service";
import {UserAuthParam} from "../../../model/userAuthParam.model";
import {CustomToastService} from "../../../service/customToast.service";

@Component({
    moduleId: module.id,
    selector: 'signin',
    templateUrl:'signin.component.html',
    styleUrls: ['signin.component.css']
    })
export class SigninComponent implements OnInit{
  signInForm: FormGroup;

  constructor(private router: Router,
              private authService: AuthService,
              private formBuilder: FormBuilder,
              private toasterService: ToasterService,
              private customToastService: CustomToastService){}

  ngOnInit(): void {
    this.signInForm = this.formBuilder.group({
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['']//, [Validators.required, Validators.minLength(4)]
    });
    this.initCustomToast();
  }

  login(userAuthParam: UserAuthParam){
    console.log("login() SigninComponent, Print userAuthParam: " + userAuthParam);
    this.authService.login(userAuthParam)
    .subscribe(() => {
        this.customToastService.setMessage('Welcome on your home page!');
        this.router.navigate((['/home']));
    }, () =>  this.router.navigate(['/landing']))
  }

  validateField(field: string): boolean {
    return this.signInForm.get(field).valid || !this.signInForm.get(field).dirty;
  }

  // private initForm(): void {
  //   this.signInForm = new FormGroup({
  //     email:  new FormControl(),
  //     password: new FormControl()
  //   })
  // }

  initCustomToast(): void {
    if(this.customToastService.getMessage() != null){
      this.popToast(this.customToastService.getMessage());
      this.customToastService.setMessage(null);
    }
  }

  public config: ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center',
    animation: 'fade'
  });

  popToast(message: string) {
    let toast: Toast = {
      type: 'info',
      title: message,
      body: '',
      showCloseButton: true
    };
    this.toasterService.popAsync(toast);
  }

}
