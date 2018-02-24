import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
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
import {PublishSiteInfoComponent} from './components/admin/publishSiteInfo/publishSiteInfo.component';
import {UserService} from "./service/user.service";

import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {HomeComponent} from "./components/home/home.component";
import {PrivatePageGuardService} from "./service/privatePageGuard.servise";
import {ToasterModule} from 'angular2-toaster';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenService} from "./service/token.service";
import {UpdateuserprofileComponent} from './components/home/updateuserprofile/updateuserprofile.component';
import {AdminComponent} from './components/admin/admin.component';
import {OfficeService} from './service/office.service';
import {EditOfficeComponent} from './components/admin/adminOffice/editOffice/editOffice.component';
import {EmployeeService} from "./service/emploee.service";
import {EditEmployeeComponent} from "./components/admin/adminEmp/editEmployee/editEmployee.component";

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
    PublishSiteInfoComponent,
    AdminComponent,
    EditOfficeComponent,
    EditEmployeeComponent
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
    PrivatePageGuardService,
    TokenService,
    OfficeService,
    EmployeeService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
