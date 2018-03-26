import {Component, OnInit} from '@angular/core';
import {Advert} from '../../../model/advert.model';
import {AdvertService} from "../../../service/advert.service";


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
  showRolesFilter = false;
  page: number = 1;
  perPage: number = 15;

  constructor(private advertService: AdvertService) {
  }

  ngOnInit(): void {
    this.getAdverts();
  }

  getAdverts(): void {
    console.log('getAdverts()');
    this.advertService.getAllAdverts().subscribe((adverts: Advert[]) => this.adverts = adverts)
  }

  removeAdvert(advert: Advert): void {
    console.log('advert id: ' + advert.id);
    let id = advert.id;
    this.adverts = this.adverts.filter(h => h !== advert);
    this.advertService.deleteAdvert(id).subscribe();
  }

  addTypeToFilter(type) {
    this.types.push(type);
    console.log(this.types);
  }

  deleteTypeFromFilter(type) {
    this.types.splice(this.types.indexOf(type), 1);
    console.log(this.types);
  }

  getAdvertsSortedBy(): void {
    if (this.types.length != 0) {
      this.getAdvertsSortedAndFilterBy();
    } else {
      console.log('getAdvertsSortedBy(' + this.sortedField + ' asc = ' + this.asc + ')');
      this.advertService.getAllAdvertsSortedBy(this.sortedField, this.asc)
        .subscribe((adverts: Advert[]) => this.adverts = adverts);
    }
  }

  getAdvertsSortedAndFilterBy(): void {
    if (this.types.length == 0) {
      this.getAdvertsSortedBy();
    } else {
      console.log('getAdvertsSortedAndFilterBy(' + this.sortedField + ' asc = ' + this.asc + 'filterBy=' + this.types + ')');
      this.advertService.getAllAdvertsSortedAndFilterBy(this.sortedField, this.asc, this.types)
        .subscribe((adverts: Advert[]) => this.adverts = adverts);
    }
  }

}
