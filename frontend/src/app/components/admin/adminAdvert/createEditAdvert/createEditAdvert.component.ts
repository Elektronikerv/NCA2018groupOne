import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";


import {Advert} from "../../../../model/advert.model";
import {AdvertType} from "../../../../model/advertType.model";
import {ADVERT_TYPES} from "../../../../model/advertType.model";
import {AdvertService} from "../../../../service/advert.service";
import {User} from "../../../../model/user.model";
import {AuthService} from '../../../../service/auth.service';


@Component({
  moduleId: module.id,
  selector: 'createEditAdvert',
  templateUrl: 'createEditAdvert.component.html',
  styleUrls: ['createEditAdvert.component.css']
})
export class CreateEditAdvertComponent implements OnInit {
  types : AdvertType[] = ADVERT_TYPES;
  createAdvertForm: FormGroup;
  adminForm: FormGroup;
  user: User;
  admin : User;
  advert : Advert;
  constructor(
              private router: Router,
              private activatedRouter: ActivatedRoute,
              private formBuilder: FormBuilder,
              private advertService: AdvertService,
              private authService: AuthService) {
    this.authService.currentUser().subscribe((user: User) => this.admin = user);
  }

  ngOnInit() {
    this.getAdvert();
    this.createAdvertForm = this.formBuilder.group({
      header: ['', [Validators.required, Validators.minLength(5)]],
      text: ['', [Validators.required, Validators.minLength(10)]],
      type:  new FormControl(null, [Validators.required]),
      // admin: this.initAdmin(),
    });
  }

  initAdmin(){
    return this.adminForm = this.formBuilder.group({
      admin: ['', Validators.required]
    })
  }

  getAdvert() {
    this.advert = this.advertService.getEmptyAdvert();
    let id: number = + this.activatedRouter.snapshot.paramMap.get('id');
    if(id != 0){
      console.log('getAdvert() id: ' + id);
      this.advertService.getAdvertById(id).subscribe((advert: Advert) => this.advert = advert);
    }
  }

  save(): void {
    console.log('Create/update Advert(advert: Advert) advert: ' + this.advert.header);
    this.advert.admin = this.admin;
    if(this.advert.id == null){
      this.advertService.createAdvert(this.advert).subscribe((advert: Advert) => {
        this.router.navigate(['admin/adminAdvert']);
      });
    } else {
      this.advertService.updateAdvert(this.advert)
        .subscribe((advert: Advert) => {
          this.router.navigate(['admin/adminAdvert']);
        });
    }
  }

  validateField(field: string): boolean {
    console.log(this.createAdvertForm.get(field));
    return this.createAdvertForm.get(field).valid || !this.createAdvertForm.get(field).dirty;
  }

}
