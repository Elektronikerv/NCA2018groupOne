import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import "rxjs/Rx";
import { HttpClient} from "@angular/common/http";

const url = '/api/reports';

@Injectable()
export class ReportService {
  constructor(private http: HttpClient) {
  }

  getManagerPDFReport(id: number, startDate: string, endDate: string ): Observable<Blob>{
    return this.http.get(`${url}/managerPDFReport?id=${id}&startDate=${startDate}&endDate=${endDate}`,
      { headers: {'Accept': 'application/pdf'}, responseType: 'blob'})

  }
}
