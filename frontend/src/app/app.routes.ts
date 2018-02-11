import {Routes} from '@angular/router';
import {SigninComponent} from './components/signin/signin.component';
import {LandingComponent} from './components/landing/landing.component';
import {SignupComponent} from './components/signup/signup.component'

export const appRoutes: Routes =[
  {
    path: '',
    redirectTo: 'landing',
    pathMatch: 'full'
  }
  ,
  {
    path: 'landing',
    component: LandingComponent
  }
  ,
  {
    path: 'signin',
    component: SigninComponent
  }
  ,
  {
    path: 'signup',
    component: SignupComponent
  }
];