import {Routes} from '@angular/router';
import {SigninComponent} from './components/signin/signin.component';
import {LandingComponent} from './components/landing/landing.component';
import {SignupComponent} from './components/signup/signup.component'
import {NewsComponent} from './components/news/news.component'
import {HomeComponent} from "./components/home/home.component";
import {AdminEmpComponent} from './components/admin/adminEmp/adminEmp.component';
import {AdminOfficeComponent} from './components/admin/adminOffice/adminOffice.component';
import {CudOfficeComponent} from './components/admin/adminOffice/cudOffice/cudOffice.component';
import {CudEmpComponent} from './components/admin/adminEmp/cudEmp/cudEmp.component';
import {PublishSiteInfoComponent} from './components/admin/publishSiteInfo/publishSiteInfo.component';

import {PrivatePageGuardService} from "./service/privatePageGuard.servise";
import {UpdateuserprofileComponent} from "./components/home/updateuserprofile/updateuserprofile.component";
import {AdminComponent} from "./components/admin/admin.component";
import {EditOfficeComponent} from "./components/admin/adminOffice/editOffice/editOffice.component";
import {EditEmployeeComponent} from "./components/admin/adminEmp/editEmployee/editEmployee.component";

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'landing',
    pathMatch: 'full'
  },
  {
    path: 'landing',
    component: LandingComponent
  },
  {
    path: 'signin',
    component: SigninComponent
  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'news',
    component: NewsComponent
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
    path: 'admin',
    component: AdminComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/adminEmp',
    component: AdminEmpComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/publishSiteInfo',
    component: PublishSiteInfoComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/adminOffice',
    component: AdminOfficeComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/cudOffice',
    component: CudOfficeComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/cudEmp',
    component: CudEmpComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/editOffice/:id',
    component:EditOfficeComponent,
    canActivate: [PrivatePageGuardService]
  },
  {
    path: 'admin/editEmployee/:id',
    component:EditEmployeeComponent,
    canActivate: [PrivatePageGuardService]
  }
];
