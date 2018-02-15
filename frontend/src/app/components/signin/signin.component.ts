import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {UserAuthParam} from "../../model/userAuthParam.model";

@Component({
    moduleId: module.id,
    selector: 'signin',
    templateUrl:'signin.component.html',
    styleUrls: ['signin.component.css']
    })
export class SigninComponent implements OnInit{
  signInForm: FormGroup;

  constructor(private router: Router, private authService: AuthService){}

  ngOnInit(): void {
    this.initForm();
  }

  login(userAuthParam: UserAuthParam){
    console.log("Print userAuthParam: " + userAuthParam);
    this.authService.login(userAuthParam)
    .subscribe(() => {
        this.router.navigate((['/home']));
    }, () =>  this.router.navigate(['/landing']))
  }

  private initForm(): void {
    this.signInForm = new FormGroup({
      email:  new FormControl(),
      password: new FormControl()
    })
  }

}
