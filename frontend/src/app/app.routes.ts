import {Routes} from '@angular/router';
import {SigninComponent} from './components/signin/signin.component';
import {LandingComponent} from './components/landing/landing.component';
import {SignupComponent} from './components/signup/signup.component'
import {HomeComponent} from "./components/home/home.component";
import {NewsComponent} from './components/news/news.component';
import {AdminEmpComponent} from './components/admin/adminEmp/adminEmp.component';
import {AdminOfficeComponent} from './components/admin/adminOffice/adminOffice.component';
import {CudOfficeComponent} from './components/admin/adminOffice/cudOffice/cudOffice.component';
import {CudEmpComponent} from './components/admin/adminEmp/cudEmp/cudEmp.component';
import {PublishSiteInfoComponent} from './components/admin/publishSiteInfo/publishSiteInfo.component';


import {PrivatePageGuardService} from "./service/privatePageGuard.servise";
import {UpdateuserprofileComponent} from "./components/home/updateuserprofile/updateuserprofile.component";

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
  },
  {
    path: 'updateUserProfile',
    component: UpdateuserprofileComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [PrivatePageGuardService]
  },

  {
    path: 'admin/adminEmp',
    component: AdminEmpComponent
  }
  ,
  {
    path: 'admin/publishSiteInfo',
    component: PublishSiteInfoComponent
  }
  ,
  {
    path: 'admin/adminOffice',
    component: AdminOfficeComponent
  }
  ,
  {
    path: 'admin/cudOffice',
    component: CudOfficeComponent
  }
  ,
  {
    path: 'admin/cudEmp',
    component: CudEmpComponent
  }



];
