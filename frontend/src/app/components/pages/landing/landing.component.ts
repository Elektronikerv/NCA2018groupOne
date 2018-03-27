import {Component, OnInit} from '@angular/core';
import {CustomToastService} from "../../../service/customToast.service";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";

@Component({
  moduleId: module.id,
  selector: 'landing',
  templateUrl: 'landing.component.html',
  styleUrls: ['landing.component.css']
})
export class LandingComponent implements OnInit {


  constructor(private customToastService: CustomToastService,
              private toasterService: ToasterService) {
  }

  ngOnInit(): void {
    this.initCustomToast();
  }

  initCustomToast(): void {
    if(this.customToastService.getMessage() != null){
      this.popToast(this.customToastService.getMessage());
      this.customToastService.setMessage(null);
    }
  }

  public config: ToasterConfig = new ToasterConfig({
    positionClass: 'toast-top-center',
    animation: 'fade'
  });

  popToast(message: string) {
    let toast: Toast = {
      type: 'info',
      title: message,
      body: '',
      showCloseButton: true
    };
    this.toasterService.popAsync(toast);
  }

}
