import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {UserService} from "../../../service/user.service";
import {PasswordService} from "../../../service/password.service";

@Component({
  moduleId: module.id,
  selector: 'upd-password',
  templateUrl: 'updPassword.component.html',
  styleUrls: ['updPassword.component.css']
})
export class UpdPasswordComponent implements OnInit {
  user: User;
  password: string;
  passwordForm: FormGroup;

  constructor(private location: Location,
              private router: Router,
              private authService: AuthService,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private passwordService: PasswordService) {
    this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  ngOnInit() {
    return this.passwordForm = this.formBuilder.group({
      // currentPassword: ['', [Validators.required, Validators.minLength(8)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
    }, {validator: this.passwordService.passwordConfirming});
  }

  validateField(field: string): boolean {
    return this.passwordForm.get([field]).valid || !this.passwordForm.get([field]).dirty;
  }

  // checkCurrentPass(): boolean{
  //   return this.passwordForm.get(['currentPassword']).value != this.passwordForm.get(['confirmPassword']).value && this.passwordForm.get(['confirmPassword']).value != null;
  // }

  checkPass(): boolean{
    return this.passwordForm.get(['password']).value !=
      this.passwordForm.get(['confirmPassword']).value
      && this.passwordForm.get(['confirmPassword']).value != null;
  }

  save(): void {
      this.password = this.passwordForm.get(['password']).value;
      console.log('Save() password: ' + this.user.firstName);
      this.user.password = this.password;
      this.userService.updateUserInfo(this.user)
        .subscribe((user: User) => { this.router.navigate(['home']);
    })
  }

  goBack(): void {
    this.location.back();
  }

}
