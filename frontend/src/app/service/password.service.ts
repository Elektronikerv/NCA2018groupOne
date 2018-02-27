import {AbstractControl} from "@angular/forms";
import {Injectable} from "@angular/core";

@Injectable()
export class PasswordService{

  constructor(){

  }

  passwordConfirming(c: AbstractControl): { invalid: boolean } {

    if (c.get('password').value !== c.get('confirmPassword').value)  {
      return {invalid: true};
    }
  }

}
