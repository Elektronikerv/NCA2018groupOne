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
import {CreateAdvertComponent} from "./components/admin/adminAdvert/createAdvert/createAdvert.component";
import {EditAdvertComponent} from "./components/admin/adminAdvert/editAdvert/editAdvert.component";
import {OrderHistoryService} from './service/orderHistory.service';
import {OrderHistoryComponent} from './components/order-history/order-history.component';

import {EmployeeService} from "./service/emploee.service";
import {EditEmployeeComponent} from "./components/admin/adminEmp/editEmployee/editEmployee.component";
import {NoprivilegeComponent} from './components/noprivilege/noprivilege.component';
import {VerifyEmailComponent} from './components/verify-email/verify-email.component';
import {AdminpageguardService} from "./service/guard/adminpageguard.service";
import {NotauthpageguardService} from "./service/guard/notauthpageguard.service";

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
    CreateAdvertComponent,
    EditAdvertComponent,
    OrderHistoryComponent,
    EditEmployeeComponent,
    NoprivilegeComponent,
    VerifyEmailComponent,
    NoprivilegeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    ToasterModule.forRoot()
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
    NotauthpageguardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
