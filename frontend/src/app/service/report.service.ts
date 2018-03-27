import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import "rxjs/Rx";
import {User} from "../model/user.model";
import {TokenService} from "./token.service";

const url = '/api/reports';

@Injectable()
export class ReportService {
  constructor(private tokenService: TokenService<User>) {
  }

  getOfficeStatisticReport(startDate: string, endDate: string): Observable<Blob> {
    let params: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getPdfWidthParams(`${url}/officeStatisticReport`, params);
  }

  getClientStatisticReport(startDate: string, endDate: string): Observable<Blob> {
    let params: Array<[string, string]> = [['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getPdfWidthParams(`${url}/clientStatisticReport`, params);
  }

  getPersonalCourierStatisticReport(id: number, startDate: string, endDate: string): Observable<Blob> {
    let params: Array<[string, any]> = [['id', id], ['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getPdfWidthParams(`${url}/personalCourierStatisticReport`, params);
  }

  getPersonalCCAgentStatisticReport(id: number, startDate: string, endDate: string): Observable<Blob> {
    let params: Array<[string, any]> = [['id', id], ['startDate', startDate], ['endDate', endDate]];
    return this.tokenService.getPdfWidthParams(`${url}/personalCCAgentStatisticReport`, params);
  }

  getPersonalInformationReport(id: number): Observable<Blob> {
    let params: Array<[string, number]> = [['id', id]];
    return this.tokenService.getPdfWidthParams(`${url}/personalInformationReport?id=${id}`, params);
  }
}
