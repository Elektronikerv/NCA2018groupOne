import {Component, OnInit} from '@angular/core';
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {Role} from "../../../model/role.model";
import {ROLES} from "../../../mock-roles";

@Component({
    moduleId: module.id,
    selector: 'navscroller',
    templateUrl:'navscroller.component.html',
    styleUrls: ['navscroller.component.css']
    })
export class NavscrollerComponent implements OnInit{

  currentUserID: number;
  roles : Role[] = ROLES;
  isAdmin: boolean = false;
  isManager: boolean = false;
  isCcagent: boolean = false;
  isCourier: boolean = false;
  isClient: boolean = false;
  user : User = <User>{};
  constructor(private authService: AuthService) {
    this.authService.currentUser().subscribe((user: User) => {
      this.user = user;
      this.currentUserID = user.id;
      if(this.user.roles.includes(this.roles[0].name)){
        this.isAdmin = true;
      }
      if(this.user.roles.includes(this.roles[1].name)){
        this.isManager = true;
      }
      if(this.user.roles.includes(this.roles[2].name)){
        this.isCcagent = true;
      }
      if(this.user.roles.includes(this.roles[3].name)){
        this.isCourier = true;
      }
      if(this.user.roles.includes(this.roles[4].name) || this.user.roles.includes(this.roles[5].name))
      {
        this.isClient = true;
      }

    });
  }

  ngOnInit(): void{


    // this.isAdmin =  this.userRoles.includes(this.roles[0].name);
    // this.isManager = this.userRoles.includes(this.roles[1]);
    // this.isCcagent = this.userRoles.includes(this.roles[2]);
    // this.isCourier = this.userRoles.includes(this.roles[3]);
    // this.isClient = (this.userRoles.includes(this.roles[4]) || this.userRoles.includes(this.roles[5]));

  }

}
