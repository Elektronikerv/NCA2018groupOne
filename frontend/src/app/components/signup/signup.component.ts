import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {FormControl, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";


@Component({
    moduleId: module.id,
    selector: 'signup',
    templateUrl:'signup.component.html',
    styleUrls: ['signup.component.css']
    })
export class SignupComponent implements OnInit{
  userRegisterForm: FormGroup;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.initForm();
  }

  submitForm(user: User):void{
    console.log(user);
    this.userService.create(user).subscribe((user: User) => {
      this.router.navigate(['/landing'])
    });
  }

  private initForm(): void {
    this.userRegisterForm = new FormGroup({
      firstName: new FormControl(),
      lastName:  new FormControl(),
      email:  new FormControl(),
      phoneNumber: new FormControl(),
      password: new FormControl()
    })
  }


}
