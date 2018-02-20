<<<<<<< HEAD
import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    selector: 'navbar',
    templateUrl:'navbar.component.html',
    styleUrls: ['navbar.component.css']
    })
export class NavbarComponent implements OnInit{
  switcherSession: boolean;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.switcherSession = this.authService.checkSignIn();
    console.log('navbar, switcherSession: ' + (this.switcherSession = this.authService.checkSignIn()))
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/signin']);
  }

=======
import {Component} from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css']
})
export class NavbarComponent {

>>>>>>> adminFunctionalityOffices
}
