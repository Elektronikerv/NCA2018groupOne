import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

import {CustomValidators} from "ng2-validation";
import {ToasterConfig, ToasterService} from "angular2-toaster";
import {AuthService} from "../../../service/auth.service";
import {UserAuthParam} from "../../../model/userAuthParam.model";

@Component({
    moduleId: module.id,
    selector: 'signin',
    templateUrl:'signin.component.html',
    styleUrls: ['signin.component.css']
    })
export class SigninComponent implements OnInit{
  signInForm: FormGroup;

  constructor(private router: Router, private authService: AuthService, private formBuilder: FormBuilder, private toasterService: ToasterService){}

  ngOnInit(): void {
    this.signInForm = this.formBuilder.group({
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['']//, [Validators.required, Validators.minLength(4)]
    })
  }

  public config1 : ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center'
  });

  login(userAuthParam: UserAuthParam){
    console.log("login() SigninComponent, Print userAuthParam: " + userAuthParam);
    this.authService.login(userAuthParam)
    .subscribe(() => {
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

}
