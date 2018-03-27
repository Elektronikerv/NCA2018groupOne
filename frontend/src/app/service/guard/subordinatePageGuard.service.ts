

import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../auth.service";
import {User} from "../../model/user.model";
import {ROLES} from "../../mock-roles";
import {Role} from "../../model/role.model";

@Injectable()
export class SubordinatePageGuardService implements CanActivate {

  user: User;
  CALL_CENTER_AGENT : Role = ROLES[2];
  COURIER : Role = ROLES[3];


  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.checkSignIn()) {
      this.authService.currentUser().subscribe((user: User) => {
        this.user = user;
        if (!this.user.roles.includes(this.COURIER.name) && !this.user.roles.includes(this.CALL_CENTER_AGENT.name)) {
          this.router.navigate(['/noprivilege']);
        }
      });
    }
    return true;
  }

}
