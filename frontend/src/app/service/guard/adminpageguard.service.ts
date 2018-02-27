import {Injectable} from '@angular/core';
import {User} from "../../model/user.model";
import {AuthService} from "../auth.service";
import {CanActivate, CanActivateChild, Router} from "@angular/router";

@Injectable()
export class AdminpageguardService implements CanActivate, CanActivateChild {

  user: User;

  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.checkSignIn()) {
      this.authService.currentUser().subscribe((user: User) => {
        this.user = user;
        if (!this.user.roles.includes('ADMIN')) {
          this.router.navigate(['/noprivilege']);
        }
      });
      return true;
    }
    return false;
  }

  canActivateChild(): boolean {
    if (this.authService.checkSignIn()) {
      this.authService.currentUser().subscribe((user: User) => {
        this.user = user;
        if (!this.user.roles.includes('ADMIN')) {
          this.router.navigate(['/noprivilege']);
        }
      });
      return true;
    }
    return false;
  }

}
