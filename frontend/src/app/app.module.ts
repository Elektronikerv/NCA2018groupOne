import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {appRoutes} from './app.routes';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';

import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {AdminEmpComponent} from './components/admin/adminEmp/adminEmp.component';
import {AdminOfficeComponent} from './components/admin/adminOffice/adminOffice.component';
import {CudOfficeComponent} from './components/admin/adminOffice/cudOffice/cudOffice.component';
import {CudEmpComponent} from './components/admin/adminEmp/cudEmp/cudEmp.component';
import {AdminAdvertComponent} from './components/admin/adminAdvert/adminAdvert.component';


import {UserService} from "./service/user.service";
import {AdvertService} from "./service/advert.service";

import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {ClientPageGuardService} from "./service/guard/clientPageGuard.servise";
import {ToasterModule} from 'angular2-toaster';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenService} from "./service/token.service";


import {AdminComponent} from './components/admin/admin.component';
import {OfficeService} from './service/office.service';
import {EditOfficeComponent} from './components/admin/adminOffice/editOffice/editOffice.component';
import {CreateEditAdvertComponent} from "./components/admin/adminAdvert/createEditAdvert/createEditAdvert.component";
import {OrderHistoryService} from './service/orderHistory.service';

import {EmployeeService} from './service/emploee.service';
import {EditEmployeeComponent} from './components/admin/adminEmp/editEmployee/editEmployee.component';
import {AdminpageguardService} from './service/guard/adminpageguard.service';
import {NotauthpageguardService} from './service/guard/notauthpageguard.service';
import {CcagentComponent} from './components/ccagent/ccagent.component';
import {OrderService} from './service/order.service';
import {PasswordService} from './service/password.service';
import {CommonModule} from '@angular/common';
import {AgmCoreModule} from '@agm/core';
import {CourierComponent} from './components/courier/courier.component';

import {CourierService} from "./service/сourier.service";
import {CcagentPageGuardService} from "./service/guard/ccagentPageGuard.service";
import {CourierPageGuardService} from "./service/guard/courierPageGuard.service";
import {ManagerPageGuardService} from "./service/guard/managerPageGuard.service";
import {UnverifiedPageGuardService} from "./service/guard/unverifiedPageGuard.service";


import {StatisticsComponent} from "./components/manager/statistics/statistics.component";
import {ViewEmployeeComponent} from "./components/manager/viewEmp/viewEmp.component";
import {ManagerEmpComponent} from "./components/manager/managerEmp.component";
import {ManagerService} from "./service/manager.service";
import {PasswordRecoveryService} from "./service/password-recovery.service";
import {YearStatisticComponent} from "./components/manager/yearStatistic/yearStatistic.component";
import {ReportService} from "./service/report.service";
import {EmployeeSearchComponent} from "./components/employee-search/employee-search.component";
import {YearStatOrderBy} from "./components/utils/comparators/yearStatOrderBy";
import {LandingComponent} from "./components/pages/landing/landing.component";
import {SigninComponent} from "./components/pages/signin/signin.component";
import {SignupComponent} from "./components/pages/signup/signup.component";
import {VerifyEmailComponent} from "./components/pages/verify-email/verify-email.component";
import {NavscrollerComponent} from "./components/navbar/navscroller/navscroller.component";
import {HomeComponent} from "./components/client/home/home.component";
import {NewsComponent} from "./components/pages/news/news.component";
import {OrderHistoryComponent} from "./components/client/order-history/order-history.component";
import {UpdPasswordComponent} from "./components/client/home/password/updPassword.component";
import {CreateOrderComponent} from "./components/client/create-order/create-order.component";
import {GoogleMapsComponent} from "./components/utils/google-maps/google-maps.component";
import {NoPrivilegeComponent} from "./components/pages/no-privilege/no-privilege.component";
import {PasswordRecoveryComponent} from "./components/pages/password-recovery/password-recovery.component";
import {EmployeesOrderBy} from "./components/utils/comparators/employeesOrderBy";
import {OfficesOrderBy} from "./components/utils/comparators/officesOrderBy";
import {OrdersOrderBy} from "./components/utils/comparators/ordersOrderBy";
import {AdvertsOrderBy} from "./components/utils/comparators/advertsOrderBy";
import {OrderHistoryOrderBy} from "./components/utils/comparators/orderHistoryOrderBy";
import {CcagentOrdersPipe} from "./components/utils/filtration/ccagent-orders.pipe";
import {AvailableOrdersPipe} from "./components/utils/filtration/available-orders.pipe";
import {EmpManagerOrderBy} from "./components/utils/comparators/empManagerOrderBy";
import {StatisticOrderBy} from "./components/utils/comparators/statisticOrderBy";
import {RolesFilterBy} from "./components/utils/filtration/rolesFilterBy";
import {AdvertsTypeFilterBy} from "./components/utils/filtration/advertsTypeFilterBy";
import {OrderStatusFilterBy} from "./components/utils/filtration/orderStatusFilterBy";

import {EditCCOrderClientComponent} from "./components/client/edit-c-c-order/edit-c-c-order-client.component";
import {WorkingDayService} from "./service/workingday.service";
import {CalendarComponent} from "./components/manager/calendar/calendar.component";
import {EditOCOrderCcagentComponent} from "./components/ccagent/edit-o-c-order-ccagent/edit-o-c-order-ccagent.component";
import {EditOCOrderClientComponent} from "./components/client/edit-o-c-order/edit-o-c-order-client.component";
import {EditCCOrderCcagentComponent} from "./components/ccagent/edit-c-c-order-ccagent/edit-c-c-order-ccagent.component";
import {DateValidatorService} from "./service/date-validator.service";
import {TimeTMPipe} from "./components/utils/TimeTransformPipe";
import {ViewOrderComponent} from "./components/client/view-order/view-order.component";
import {EmpCalendarComponent} from "./components/client/home/empCalendar/empCalendar.component";
import { OrderStatistic } from "./components/manager/order-statistic/order-statistic.component";


@NgModule({
  declarations: [
    AppComponent,

    LandingComponent,
    SigninComponent,
    SignupComponent,
    VerifyEmailComponent,

    NavbarComponent,
    NavscrollerComponent,

    HomeComponent,
    NewsComponent,

    AdminEmpComponent,
    AdminOfficeComponent,
    CudOfficeComponent,
    CudEmpComponent,
    AdminComponent,
    EditOfficeComponent,
    AdminAdvertComponent,
    CreateEditAdvertComponent,
    EditEmployeeComponent,
    EmployeeSearchComponent,
    EmpCalendarComponent,


    OrderHistoryComponent,
    UpdPasswordComponent,
    CreateOrderComponent,
    ViewOrderComponent,

    CcagentComponent,
    EditOCOrderCcagentComponent,
    EditOCOrderClientComponent,
    EditCCOrderCcagentComponent,
    EditCCOrderClientComponent,

    CourierComponent,

    ManagerEmpComponent,
    ViewEmployeeComponent,
    StatisticsComponent,
    YearStatisticComponent,
    CalendarComponent,

    GoogleMapsComponent,
    NoPrivilegeComponent,
    PasswordRecoveryComponent,

    EmployeesOrderBy,
    OfficesOrderBy,
    OrdersOrderBy,
    AdvertsOrderBy,
    OrderHistoryOrderBy,

    CcagentOrdersPipe,
    AvailableOrdersPipe,
    EmpManagerOrderBy,

    TimeTMPipe,
    StatisticOrderBy,
    RolesFilterBy,
    AdvertsTypeFilterBy,
    OrderStatusFilterBy,
    YearStatOrderBy,
    OrderStatistic
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    ToasterModule.forRoot(),
    CommonModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBosHI7J2BNwC_oQb61lOmHcIh9Urt28Es',
      libraries: ['places'],
      region: 'UA',
      language: 'en'
    }),
    NgxPaginationModule
  ],
  providers: [
    AuthService,
    TokenService,

    UserService,
    OfficeService,
    AdvertService,
    OrderHistoryService,
    EmployeeService,
    OrderService,
    PasswordService,
    CourierService,
    ManagerService,
    WorkingDayService,
    DateValidatorService,

    ClientPageGuardService,
    AdminpageguardService,
    CcagentPageGuardService,
    CourierPageGuardService,
    ManagerPageGuardService,
    NotauthpageguardService,
    UnverifiedPageGuardService,
    PasswordRecoveryService,
    ReportService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
