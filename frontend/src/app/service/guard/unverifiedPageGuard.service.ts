import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../auth.service";
import {User} from "../../model/user.model";
import {ROLES} from "../../mock-roles";
import {Role} from "../../model/role.model";

@Injectable()
export class UnverifiedPageGuardService implements CanActivate {

  user: User;
  UNVERIFIED_CLIENT : Role = ROLES[6];

  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.checkSignIn()) {
      this.authService.currentUser().subscribe((user: User) => {
        this.user = user;
        if (!this.user.roles.includes(this.UNVERIFIED_CLIENT.name)) {
          this.router.navigate(['/verifyEmail']);
        }
      });
    }
    return true;
  }

}
