import {Injectable} from "@angular/core";
import {Advert} from "../model/advert.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";

const url = '/api/advert';

@Injectable()
export class AdvertService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<Advert>  ) {
  }

  createAdvert(advert: Advert): Observable<Advert> {
    console.log('createAdvert(advert: Advert) advert: ' + advert);
    return this.tokenService.post(url, advert);
  }

  getAdverts(): Observable<Advert[]> {
    console.log('getAdverts()');
    return this.tokenService.get(url);
  }

  deleteAdvert(id: number): Observable<Advert> {
    console.log('deleteAdvert(advert) id: ' + id);
    return this.tokenService.delete(`${url}/${id}`);
  }

  getAdvertById(id: number): Observable<Advert> {
    return this.tokenService.get(`${url}/${id}`);
  }

  updateAdvert(advert: Advert): Observable<Advert> {
    console.log('Update(advert) advert.header advert.header: ' + advert.id + ' '+ advert.header );
    return this.tokenService.put(`${url}/${advert.id}`, advert);
  }
}
