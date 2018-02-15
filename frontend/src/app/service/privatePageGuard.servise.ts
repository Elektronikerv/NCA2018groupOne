import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "./auth.service";

@Injectable()
export class PrivatePageGuardService implements CanActivate{


  constructor(private router: Router, private authService: AuthService) {}

  canActivate(): boolean{
    if(!this.authService.checkSignIn()){
      this.router.navigate(['/signin']);
    }
    return true;
  }



}
