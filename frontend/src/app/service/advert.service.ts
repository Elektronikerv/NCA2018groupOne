import {Injectable} from "@angular/core";
import {Advert} from "../model/advert.model";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {User} from "../model/user.model";
import {UserService} from "./user.service";
import {AdvertType} from "../model/advertType.model";

const url = '/api/advert';

@Injectable()
export class AdvertService {

  constructor(private http: HttpClient,
              private tokenService: TokenService<Advert>,
              private userService: UserService) {
  }

  createAdvert(advert: Advert): Observable<Advert> {
    console.log('createAdvert(advert: Advert) advert: ' + advert);
    return this.tokenService.post(url, advert);

  }

  getAdverts(type: AdvertType): Observable<Advert[]> {
    console.log('Service getAdverts(type) with type ' + type.name);
    return this.http.get<Advert[]>(`${url}/type/${type.id}`);
  }

  getAllAdverts(): Observable<Advert[]> {
    console.log('getAllAdverts()');
    return this.http.get<Advert[]>(url, {headers: {}});
  }

  getAllAdvertsSorted(sortedField: string, asc: boolean): Observable<Advert[]> {
    console.log('getAdvertsSorted(' + sortedField + ' asc = ' + asc + ')');
    const params: Array<[string, any]> = [['sortedField', sortedField], ['asc', asc]];
    return this.tokenService.getWithParams(`${url}/sort`, params);
  }

  getAllAdvertsFilteredAndSorted(sortedField: string, asc: boolean, advertTypes: string[]): Observable<Advert[]> {
    console.log('getAdvertsFilteredAndSorted(' + sortedField + ' asc = ' + asc + 'advertTypes = ' + advertTypes + ')');
    const params: Array<[string, any]> = [['sortedField', sortedField], ['asc', asc], ['advertTypes', advertTypes]];
    return this.tokenService.getWithParams(`${url}/filter`, params);
  }

  deleteAdvert(id: number): Observable<Advert> {
    console.log('deleteAdvert(advert) id: ' + id);
    return this.tokenService.delete(`${url}/${id}`);
  }

  getAdvertById(id: number): Observable<Advert> {
    return this.tokenService.get(`${url}/${id}`);
  }

  updateAdvert(advert: Advert): Observable<Advert> {
    console.log('Update(advert) advert.header advert.header: ' + advert.id + ' ' + advert.header);
    return this.tokenService.put(`${url}/${advert.id}`, advert);
  }

  getEmptyAdvert(): Advert {
    let admin: User = UserService.getEmptyUser();
    return {id: null, header: null, text: null, admin: admin, type: null, dateOfPublishing: null}
  }

}
