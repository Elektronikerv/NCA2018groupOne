import {Component, OnInit} from '@angular/core';
import {Advert} from "../../../model/advert.model";
import {AdvertService} from "../../../service/advert.service";
import {ADVERT_TYPES, AdvertType} from "../../../model/advertType.model";
import {JwtHelper} from "angular2-jwt";

@Component({
  moduleId: module.id,
  selector: 'news',
  templateUrl: 'news.component.html',
  styleUrls: ['news.component.css']
})
export class NewsComponent implements OnInit {
  private jwtHelper: JwtHelper = new JwtHelper();
  token: string;
  advert: Advert;
  advertTypes: AdvertType[];
  adverts: Advert[] = [];
  page: number = 1;
  perPage: number = 5;

  constructor(private advertService: AdvertService) {
    this.advertTypes = ADVERT_TYPES;
  }

  ngOnInit(): void {
    this.token = localStorage.getItem("currentUser");
    this.getAllAdverts();
  }

  getAllAdverts(): void {
    console.log('getAdverts()');
    this.advertService.getAllAdverts().subscribe((adverts: Advert[]) => this.adverts = adverts)
  }

  getAdverts(type: AdvertType): void {
    console.log('getAdverts invoked with parameter' + type.name);

    this.advertService.getAdverts(type).subscribe((adverts: Advert[]) => this.adverts = adverts)
  }

}
