import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";


import {Advert} from "../../../../model/advert.model";
import {AdvertType} from "../../../../model/advertType.model";
import {ADVERT_TYPES} from "../../../../model/advertType.model";
import {AdvertService} from "../../../../service/advert.service";
import {User} from "../../../../model/user.model";
import {AuthService} from '../../../../service/auth.service';


@Component({
  moduleId: module.id,

  selector: 'createAdvert',
  templateUrl: 'createAdvert.component.html',
  styleUrls: ['createAdvert.component.css']
})

export class CreateAdvertComponent implements OnInit {
  types : AdvertType[] = ADVERT_TYPES;
  createAdvertForm: FormGroup;
  adminForm: FormGroup;
  admin : User;
  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private advertService: AdvertService,
              private authService: AuthService ) {
    this.authService.currentUser().subscribe((user: User) => this.admin = user);
}


  ngOnInit() {
    this.createAdvertForm = this.formBuilder.group({
      header: ['', [Validators.required, Validators.minLength(5)]],
      text: ['', [Validators.required, Validators.minLength(10)]],
      type: new FormControl(CustomValidators.required),
      admin: this.initAdmin(),
    });
  }

  initAdmin(){
    return this.adminForm = this.formBuilder.group({
      admin: ['', Validators.required]
    });
  }

  createAdvert(advert: Advert): void {
    console.log('createAdvert(advert: Advert) advert: ' + advert.header);
    advert.admin.id= this.admin.id;
    this.advertService.createAdvert(advert).subscribe((advert: Advert) => {
      this.router.navigate(['admin/adminAdvert']);
    })
  }

  validateField(field: string): boolean {
    console.log(this.createAdvertForm.get(field));
    return this.createAdvertForm.get(field).valid || !this.createAdvertForm.get(field).dirty;
  }


}
