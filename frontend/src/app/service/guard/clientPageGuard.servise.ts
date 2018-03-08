import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../auth.service";
import {User} from "../../model/user.model";
import {ROLES} from "../../mock-roles";
import {Role} from "../../model/role.model";

@Injectable()
export class ClientPageGuardService implements CanActivate {

  user: User;
  CLIENT : Role = ROLES[4];

  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.checkSignIn()) {
      this.authService.currentUser().subscribe((user: User) => {
        this.user = user;
        if (!this.user.roles.includes(this.CLIENT.name)) {//CLIENT
          this.router.navigate(['/noprivilege']);
        }
      });
      return true;
    }
    return false;
  }

}
