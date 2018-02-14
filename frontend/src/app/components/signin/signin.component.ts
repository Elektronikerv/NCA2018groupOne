import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserAuthParam} from "../../model/userAuthParam.model";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    selector: 'signin',
    templateUrl:'signin.component.html',
    styleUrls: ['signin.component.css']
    })
export class SigninComponent implements OnInit{
  signInForm: FormGroup;


  constructor(private router: Router){}

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.signInForm = new FormGroup({
      email:  new FormControl(),
      password: new FormControl()
    })
  }

}
