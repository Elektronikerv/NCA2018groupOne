import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import "rxjs/Rx";
import {HttpClient} from "@angular/common/http";

const url = '/api/reports';

@Injectable()
export class ReportService {
  constructor(private http: HttpClient) {
  }

  getOfficeStatisticReport(startDate: string, endDate: string): Observable<Blob> {
    return this.http.get(`${url}/officeStatisticReport?startDate=${startDate}&endDate=${endDate}`,
      {headers: {'Accept': 'application/pdf'}, responseType: 'blob'})
  }

  getClientStatisticReport(startDate: string, endDate: string): Observable<Blob> {
    return this.http.get(`${url}/clientStatisticReport?startDate=${startDate}&endDate=${endDate}`,
      {headers: {'Accept': 'application/pdf'}, responseType: 'blob'})
  }

  getPersonalCourierStatisticReport(id: number, startDate: string, endDate: string): Observable<Blob> {
    return this.http.get(`${url}/personalCourierStatisticReport?id=${id}&startDate=${startDate}&endDate=${endDate}`,
      {headers: {'Accept': 'application/pdf'}, responseType: 'blob'})
  }

  getPersonalCCAgentStatisticReport(id: number, startDate: string, endDate: string): Observable<Blob> {
    return this.http.get(`${url}/personalCCAgentStatisticReport?id=${id}&startDate=${startDate}&endDate=${endDate}`,
      {headers: {'Accept': 'application/pdf'}, responseType: 'blob'})
  }

  getPersonalInformationReport(id: number): Observable<Blob> {
    return this.http.get(`${url}/personalInformationReport?id=${id}`,
      {headers: {'Accept': 'application/pdf'}, responseType: 'blob'})
  }
}
