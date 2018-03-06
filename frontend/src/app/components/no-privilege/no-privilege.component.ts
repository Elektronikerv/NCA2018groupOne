import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-no-privilege',
  templateUrl: './no-privilege.component.html',
  styleUrls: ['./no-privilege.component.css']
})
export class NoPrivilegeComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit() {

    setTimeout((router: Router) => {
      this.router.navigate(['home']);
    }, 5000);

  }

}
