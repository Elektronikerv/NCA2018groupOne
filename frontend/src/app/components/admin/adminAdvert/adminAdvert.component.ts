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

  constructor(private advertService: AdvertService) {
  }

  ngOnInit(): void {
    this.getAdverts();
  }

  getAdverts(): void {
    console.log('getAdverts()');
    this.advertService.getAdverts().subscribe((adverts: Advert[]) => this.adverts = adverts)
  }

  removeAdvert(advert: Advert): void {
    console.log('advert id: ' + advert.id);
    let id = advert.id;
    this.adverts = this.adverts.filter(h => h !== advert);
    this.advertService.deleteAdvert(id).subscribe();
  }

}
