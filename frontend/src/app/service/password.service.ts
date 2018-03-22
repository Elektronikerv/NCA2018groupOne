import {AbstractControl, FormGroup} from "@angular/forms";
import {Injectable} from "@angular/core";

@Injectable()
export class PasswordService{

  constructor(){

  }

  passwordConfirming(c: AbstractControl): { invalid: boolean, message: string } {

    if (c.get('password').value !== c.get('confirmPassword').value)  {
      return {invalid: true, message: 'Password Should Be the Same'};
    }
  }

  passwordMatching(password: string, confirmPassword: string) {
    return (group: FormGroup): {[key: string]: any} => {
      let pass = group.controls[password];
      let confPass = group.controls[confirmPassword];
      if (pass.value !== confPass.value) {
        return {
          passwordMismatch: "Mismatch Passwords"
        };
      }
      return {};
    }
  }

}
