import {BrowserModule} from '@angular/platform-browser';
import {forwardRef, NgModule} from '@angular/core';
import {appRoutes} from './app.routes';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {LandingComponent} from './components/landing/landing.component';
import {SigninComponent} from './components/signin/signin.component';
import {SignupComponent} from './components/signup/signup.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {NavscrollerComponent} from './components/admin/navscroller/navscroller.component';
import {NewsComponent} from './components/news/news.component';
import {AdminEmpComponent} from './components/admin/adminEmp/adminEmp.component';
import {AdminOfficeComponent} from './components/admin/adminOffice/adminOffice.component';
import {CudOfficeComponent} from './components/admin/adminOffice/cudOffice/cudOffice.component';
import {CudEmpComponent} from './components/admin/adminEmp/cudEmp/cudEmp.component';
import {AdminAdvertComponent} from './components/admin/adminAdvert/adminAdvert.component';


import {UserService} from "./service/user.service";
import {AdvertService} from "./service/advert.service";

import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {HomeComponent} from "./components/home/home.component";
import {ClientPageGuardService} from "./service/guard/clientPageGuard.servise";
import {ToasterModule} from 'angular2-toaster';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenService} from "./service/token.service";


import {AdminComponent} from './components/admin/admin.component';
import {OfficeService} from './service/office.service';
import {EditOfficeComponent} from './components/admin/adminOffice/editOffice/editOffice.component';
import {CreateEditAdvertComponent} from "./components/admin/adminAdvert/createEditAdvert/createEditAdvert.component";
import {OrderHistoryService} from './service/orderHistory.service';
import {OrderHistoryComponent} from './components/order-history/order-history.component';

import {EmployeeService} from './service/emploee.service';
import {EditEmployeeComponent} from './components/admin/adminEmp/editEmployee/editEmployee.component';
import {VerifyEmailComponent} from './components/verify-email/verify-email.component';
import {AdminpageguardService} from './service/guard/adminpageguard.service';
import {NotauthpageguardService} from './service/guard/notauthpageguard.service';
import {UpdPasswordComponent} from './components/home/password/updPassword.component';
import {CcagentComponent} from './components/ccagent/ccagent.component';
import {OrderService} from './service/order.service';
import {EditOrderCcagentComponent} from './components/ccagent/edit-order-ccagent/edit-order-ccagent.component';
import {PasswordService} from './service/password.service';
import {GoogleMapsComponent} from './components/google-maps/google-maps.component';
import {CommonModule} from '@angular/common';
import {AgmCoreModule} from '@agm/core';
import {CourierComponent} from './components/courier/courier.component';

import {CourierService} from "./service/сourier.service";
import {CreateOrderComponent} from './components/create-order/create-order.component';
import {NoPrivilegeComponent} from './components/no-privilege/no-privilege.component';
import {CcagentPageGuardService} from "./service/guard/ccagentPageGuard.service";
import {CourierPageGuardService} from "./service/guard/courierPageGuard.service";
import {ManagerPageGuardService} from "./service/guard/managerPageGuard.service";
import {UnverifiedPageGuardService} from "./service/guard/unverifiedPageGuard.service";
import {EmployeesOrderBy} from "./components/comparators/employeesOrderBy";
import {OfficesOrderBy} from "./components/comparators/officesOrderBy";
import {OrdersOrderBy} from "./components/comparators/ordersOrderBy";
import {AdvertsOrderBy} from "./components/comparators/advertsOrderBy";
import {OrderHistoryOrderBy} from "./components/comparators/orderHistoryOrderBy";
import {StatisticsComponent} from "./components/manager/statistics/statistics.component";
import {ViewEmployeeComponent} from "./components/manager/viewEmp/viewEmp.component";
import {ManagerEmpComponent} from "./components/manager/managerEmp.component";
import {ManagerService} from "./service/manager.service";
import {RolesFilterBy} from "./components/filtration/rolesFilterBy";
import {AdvertsTypeFilterBy} from "./components/filtration/advertsTypeFilterBy";
import {OrderStatusFilterBy} from "./components/filtration/orderStatusFilterBy";


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


    OrderHistoryComponent,
    UpdPasswordComponent,
    CreateOrderComponent,

    CcagentComponent,
    EditOrderCcagentComponent,

    CourierComponent,

    ManagerEmpComponent,
    ViewEmployeeComponent,
    StatisticsComponent,

    GoogleMapsComponent,
    NoPrivilegeComponent,

    EmployeesOrderBy,
    OfficesOrderBy,
    OrdersOrderBy,
    AdvertsOrderBy,
    OrderHistoryOrderBy,
    RolesFilterBy,
    AdvertsTypeFilterBy,
    OrderStatusFilterBy
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
      libraries: ['places']
    })
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

    ClientPageGuardService,
    AdminpageguardService,
    CcagentPageGuardService,
    CourierPageGuardService,
    ManagerPageGuardService,
    NotauthpageguardService,
    UnverifiedPageGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
