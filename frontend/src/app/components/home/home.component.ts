import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: User;

  constructor(private authService: AuthService, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    this.authService.currentUser().subscribe((user: User) => this.user = user);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/signin']);
  }


}
