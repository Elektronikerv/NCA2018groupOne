import {Injectable} from '@angular/core';
import {User} from "../../model/user.model";
import {AuthService} from "../auth.service";
import {CanActivate, CanActivateChild, Router} from "@angular/router";
import {ROLES} from "../../mock-roles";
import {Role} from "../../model/role.model";

@Injectable()
export class AdminpageguardService implements CanActivate {

  user: User;
  ADMIN : Role = ROLES[0];
  constructor(private router: Router,
              private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.checkSignIn()) {
      this.authService.currentUser().subscribe((user: User) => {
        this.user = user;
        if (!this.user.roles.includes(this.ADMIN.name)) {
          this.router.navigate(['/noprivilege']);
        }
      });
      return true;
    }
    return false;
  }


}
