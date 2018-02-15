import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";

@Component({
    moduleId: module.id,
    selector: 'navbar',
    templateUrl:'navbar.component.html',
    styleUrls: ['navbar.component.css']
    })
export class NavbarComponent implements OnInit{


  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.flajok = this.authService.checkSignIn()
  }

  flajok: boolean;

}
