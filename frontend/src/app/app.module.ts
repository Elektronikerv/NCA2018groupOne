import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { appRoutes } from './app.routes';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LandingComponent } from './components/landing/landing.component';
import { SigninComponent } from './components/signin/signin.component';
import { SignupComponent } from './components/signup/signup.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { UserService } from "./service/user.service";
import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {HomeComponent} from "./components/home/home.component";
import {PrivatePageGuardService} from "./service/privatePageGuard.servise";
import {ToasterModule} from 'angular2-toaster';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenService} from "./service/token.service";

@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    SigninComponent,
    SignupComponent,
    NavbarComponent,
    HomeComponent
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
  providers: [UserService, AuthService, PrivatePageGuardService, TokenService],
  bootstrap: [AppComponent]
})
export class AppModule {

}
