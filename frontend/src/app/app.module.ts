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


import { UserService } from "./service/user.service";
import { AdvertService } from "./service/advert.service";

import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {HomeComponent} from "./components/home/home.component";
import {ClientPageGuardService} from "./service/guard/clientPageGuard.servise";
import {ToasterModule} from 'angular2-toaster';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenService} from "./service/token.service";
import {UpdateuserprofileComponent} from './components/home/updateuserprofile/updateuserprofile.component';
import { UpdateProfileComponent } from './components/home/updateProfile/updateProfile.component';
import {AdminComponent} from './components/admin/admin.component';
import {OfficeService} from './service/office.service';
import {EditOfficeComponent} from './components/admin/adminOffice/editOffice/editOffice.component';
import {CreateEditAdvertComponent} from "./components/admin/adminAdvert/createEditAdvert/createEditAdvert.component";
import {OrderHistoryService} from './service/orderHistory.service';
import {OrderHistoryComponent} from './components/order-history/order-history.component';

import {EmployeeService} from './service/emploee.service';
import {EditEmployeeComponent} from './components/admin/adminEmp/editEmployee/editEmployee.component';
import {NoprivilegeComponent, NoPrivilegeComponent} from './components/noPrivilege/noPrivilege.component';
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
import {AddUpdAddressComponent} from "./components/home/updateProfile/address/addUpdAddress.component";
import {FulfillmentOrderService} from "./service/fulfillmentOrder.service";
import {CreateOrderComponent} from './components/create-order/create-order.component';


@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    SigninComponent,
    SignupComponent,
    NavbarComponent,
    HomeComponent,
    UpdateuserprofileComponent,
    NavscrollerComponent,
    NewsComponent,
    AdminEmpComponent,
    AdminOfficeComponent,
    CudOfficeComponent,
    CudEmpComponent,
    AdminComponent,
    EditOfficeComponent,
    AdminAdvertComponent,
    CreateEditAdvertComponent,
    OrderHistoryComponent,
    EditEmployeeComponent,
    NoprivilegeComponent,
    VerifyEmailComponent,
    AddUpdAddressComponent,
    UpdPasswordComponent,
    CcagentComponent,
    EditOrderCcagentComponent,
    UpdateProfileComponent,
    CourierComponent,
    CreateOrderComponent,
    GoogleMapsComponent,
    CourierComponent
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
    UserService,
    AuthService,
    TokenService,
    OfficeService,
    AdvertService,
    OrderHistoryService,
    EmployeeService,
    ClientPageGuardService,
    AdminpageguardService,
    NotauthpageguardService,
    OrderService,
    PasswordService,
    FulfillmentOrderService,
    CourierService

  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
