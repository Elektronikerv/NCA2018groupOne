import {Component, OnInit} from '@angular/core';
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";

@Component({
    moduleId: module.id,
    selector: 'navscroller',
    templateUrl:'navscroller.component.html',
    styleUrls: ['navscroller.component.css']
    })
export class NavscrollerComponent {

  currentUser: User;

  constructor(private authService: AuthService) {
    this.authService.currentUser().subscribe((user: User) => this.currentUser = user);
  }
}
