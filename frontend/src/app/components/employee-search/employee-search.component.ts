import { Component, OnInit } from '@angular/core';
import {Observable, ObservableInput} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";
import {User} from "../../model/user.model";
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {EmployeeService} from "../../service/emploee.service";

@Component({
  selector: 'app-employee-search',
  templateUrl: './employee-search.component.html',
  styleUrls: ['./employee-search.component.css']
})
export class EmployeeSearchComponent implements OnInit {
  users$: Observable<User[]>;
  private searchTerms = new Subject<string>();

  constructor(private employeeService: EmployeeService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.users$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.employeeService.searchEmployees(term)),
    );
  }

}
