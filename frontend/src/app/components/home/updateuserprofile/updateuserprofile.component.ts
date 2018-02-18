import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {AuthService} from "../../../service/auth.service";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-updateuserprofile',
  templateUrl: './updateuserprofile.component.html',
  styleUrls: ['./updateuserprofile.component.css']
})
export class UpdateuserprofileComponent implements OnInit {
  user: User;

  constructor(private location: Location, private authService: AuthService, private userService: UserService) {
  }

  ngOnInit() {
    this.authService.currentUser().subscribe((user: User) => this.user = user)
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.userService.update(this.user)
      .subscribe(() => this.goBack());
  }

}
