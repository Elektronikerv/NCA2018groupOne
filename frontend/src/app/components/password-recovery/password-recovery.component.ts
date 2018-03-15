import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import { PasswordRecoveryService } from '../../service/password-recovery.service';


@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.css']
})
export class PasswordRecoveryComponent implements OnInit {

  emailForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private passwordRecoveryService: PasswordRecoveryService) { }

  ngOnInit() {
    this.emailForm = this.formBuilder.group({
      email: ['', [Validators.required, CustomValidators.email]]
    });
  }

  validateField(field: string): boolean {
    return this.emailForm.get(field).valid || !this.emailForm.get(field).dirty;
  }

  recoverPassword() {
    this.passwordRecoveryService
        .recoverPassword(this.emailForm.get('email').value)
        .subscribe();
  }

}
