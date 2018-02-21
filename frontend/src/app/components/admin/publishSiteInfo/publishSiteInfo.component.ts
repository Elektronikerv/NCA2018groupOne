import { Component , OnInit} from '@angular/core';

import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "ng2-validation";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";


@Component ({
    moduleId: module.id,

    selector: 'publishSiteInfo',
    templateUrl: 'publishSiteInfo.component.html',
    styleUrls: ['publishSiteInfo.component.css']
})

export class PublishSiteInfoComponent implements OnInit{

    siteInfoPublishForm: FormGroup;
     constructor( private router: Router, private formBuilder: FormBuilder
        // , private toasterService: ToasterService
    ) {}



    ngOnInit() {
        this.siteInfoPublishForm = this.formBuilder.group({
          header: new FormControl(CustomValidators.required),
          text: new FormControl(CustomValidators.required),
          type: new FormControl(CustomValidators.required)
        });
      }

    //   public config1 : ToasterConfig = new ToasterConfig({
    //     positionClass: 'toast-top-center'
    //   });
    
    //   popToast() {
    //     var toast: Toast = {
    //       type: 'info',
    //       title: 'Hello from Toast Title',
    //       body: 'Hello from Toast Body'
    //     };
    //     this.toasterService.pop(toast);
    //   }

      submitForm():void{

        };
        validateField(field: string): boolean {
            console.log(this.siteInfoPublishForm.get(field));
            return this.siteInfoPublishForm.get(field).valid || !this.siteInfoPublishForm.get(field).dirty;
          }
    

}