import {Component, OnInit} from '@angular/core';
import {Advert} from '../../../model/advert.model';
import {AdvertService} from "../../../service/advert.service";
import {Toast, ToasterConfig, ToasterService} from "angular2-toaster";
import {CustomToastService} from "../../../service/customToast.service";


@Component({
  moduleId: module.id,
  selector: 'adminAdvert',
  templateUrl: 'adminAdvert.component.html',
  styleUrls: ['adminAdvert.component.css']
})
export class AdminAdvertComponent implements OnInit {
  advert: Advert;
  adverts: Advert[] = [];
  sortedField = 'id';
  asc = true;
  types = [];
  typesString = '';
  showRolesFilter = false;
  page: number = 1;
  perPage: number = 15;

  constructor(private advertService: AdvertService,
              private customToastService: CustomToastService,
              private toasterService: ToasterService) {
  }

  ngOnInit(): void {
    this.getAdverts();

  }

  getAdverts(): void {
    console.log('getAdverts()');
    this.advertService.getAllAdverts().subscribe((adverts: Advert[]) => {
      this.adverts = adverts;
      this.initCustomToast();
    })
  }

  getAdvertsSortedBy(): void {
    console.log('getAdvertsSortedBy(' + this.sortedField + ' asc = ' + this.asc + ')');
    this.advertService.getAllAdvertsSortedBy(this.sortedField, this.asc)
      .subscribe((adverts: Advert[]) => this.adverts = adverts);
  }

  removeAdvert(advert: Advert): void {
    console.log('advert id: ' + advert.id);
    let id = advert.id;
    this.adverts = this.adverts.filter(h => h !== advert);
    this.advertService.deleteAdvert(id).subscribe();
  }

  addTypeToFilter(type): string[] {
    this.types.push(type);
    this.typesString = this.types.join('.');
    return this.typesString.split('.');
  }

  deleteTypeFromFilter(type): string[] {
    this.types.splice(this.types.indexOf(type), 1);
    this.typesString = this.types.join('.');
    return this.typesString.split('.').filter(type => {
      return type.length > 1
    });
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

  initCustomToast(): void {
    if(this.customToastService.getMessage() != null){
      this.popToast(this.customToastService.getMessage());
      this.customToastService.setMessage(null);
    }
  }

}
