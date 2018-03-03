import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-noprivilege',
  templateUrl: './noPrivilege.component.html',
  styleUrls: ['./noPrivilege.component.css']
})
export class NoPrivilegeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {

    setTimeout((router: Router) => {
      this.router.navigate(['home']);
    }, 5000);

  }

}
