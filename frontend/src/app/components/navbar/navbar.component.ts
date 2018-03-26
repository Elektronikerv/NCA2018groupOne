import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {CustomToastService} from "../../service/customToast.service";

@Component({
    moduleId: module.id,
    selector: 'navbar',
    templateUrl:'navbar.component.html',
    styleUrls: ['navbar.component.css']
    })
export class NavbarComponent implements OnInit{
  switcherSession: boolean;

  constructor(private authService: AuthService,
              private router: Router,
              private customToastService: CustomToastService) {}

  ngOnInit(): void {
    this.switcherSession = this.authService.checkSignIn();
    console.log('navbar, switcherSession: ' + (this.switcherSession = this.authService.checkSignIn()))
  }

  logout(){
    this.authService.logout();
    this.customToastService.setMessage('See you later!');
    this.router.navigate(['/signin']);
  }

}
