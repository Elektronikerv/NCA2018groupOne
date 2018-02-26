import {Component, OnInit} from '@angular/core';

import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {AdvertService} from "../../../../service/advert.service";

import {Advert} from "../../../../model/advert.model";
import {ActivatedRoute, Router} from "@angular/router";
import {ADVERT_TYPES, AdvertType} from "../../../../model/advertType.model";
import {User} from "../../../../model/user.model";
import {AuthService} from "../../../../service/auth.service";


@Component({
  moduleId: module.id,

  selector: 'editAdvert',
  templateUrl: 'editAdvert.component.html',
  styleUrls: ['editAdvert.component.css']
})

export class EditAdvertComponent implements OnInit {

  advert: Advert;
  types : AdvertType[];
  admin: User;
  editAdvertForm: FormGroup;
  adminForm: FormGroup;

  constructor(private activatedRouter: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private advertService: AdvertService,
              private authService: AuthService) {
    this.types = ADVERT_TYPES;
    this.authService.currentUser().subscribe((user: User) => this.admin = user);

  }

  ngOnInit() {
    this.getAdvert();
    this.editAdvertForm = this.formBuilder.group({
      header: new FormControl(CustomValidators.required),
      text: new FormControl(CustomValidators.required),
      type: new FormControl(CustomValidators.required),
      admin: this.initAdmin(),
    });
  }

  initAdmin(){
    return this.adminForm = this.formBuilder.group({
      admin: ['', Validators.required]
    });
  }

  getAdvert() {
    const id = +this.activatedRouter.snapshot.paramMap.get('id');
    console.log('getAdvert() id: ' + id);
    this.advertService.getAdvertById(id).subscribe((advert: Advert) => this.advert = advert);
  }

  save(): void {
    console.log('save() advert: ' + this.advert.header);
    this.advert.admin.id = this.admin.id;
    this.advertService.updateAdvert(this.advert)
      .subscribe((advert: Advert) => {
      this.router.navigate(['admin/adminAdvert']);
    })
  }


  validateField(field: string): boolean {
    console.log(this.editAdvertForm.get(field));
    return this.editAdvertForm.get(field).valid || !this.editAdvertForm.get(field).dirty;
  }


}
