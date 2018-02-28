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


import {ClientPageGuardService} from "./service/guard/clientPageGuard.servise";
import {UpdateuserprofileComponent} from "./components/home/updateuserprofile/updateuserprofile.component";
import {AdminComponent} from "./components/admin/admin.component";
import {NoprivilegeComponent} from "./components/noprivilege/noprivilege.component";
import {VerifyEmailComponent} from "./components/verify-email/verify-email.component";
import {AdminpageguardService} from "./service/guard/adminpageguard.service";
import {EditOfficeComponent} from "./components/admin/adminOffice/editOffice/editOffice.component";
import {AdminAdvertComponent} from "./components/admin/adminAdvert/adminAdvert.component";
import {CreateAdvertComponent} from "./components/admin/adminAdvert/createAdvert/createAdvert.component";
import {EditAdvertComponent} from "./components/admin/adminAdvert/editAdvert/editAdvert.component";
import {OrderHistoryComponent} from "./components/order-history/order-history.component";
import {EditEmployeeComponent} from "./components/admin/adminEmp/editEmployee/editEmployee.component";
import {NotauthpageguardService} from "./service/guard/notauthpageguard.service";

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
    path: 'noprivilege',
    component: NoprivilegeComponent
  },
  {
    path: 'verifyEmail',
    component: VerifyEmailComponent
  },
  {
    path: 'updateUserProfile/:id',
    component: UpdateuserprofileComponent,
    canActivate: [NotauthpageguardService]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [NotauthpageguardService]
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminpageguardService]
    // canActivateChild: [AdminpageguardService],
    // children: [
    //   {path: '', redirectTo: 'admin', pathMatch: 'full'},
    //   {path: 'adminEmp', component: AdminEmpComponent},
    //   {path: 'publishSiteInfo', component: PublishSiteInfoComponent},
    //   {path: 'adminOffice', component: AdminOfficeComponent},
    //   {path: 'cudOffice', component: CudOfficeComponent},
    //   {path: 'cudEmp', component: CudEmpComponent},
    //   {path: 'editOffice/:id', component: EditOfficeComponent},
    //   {path: 'editEmployee/:id', component: EditEmployeeComponent},
    // ]
  },
  {
    path: 'admin/adminEmp',
    component: AdminEmpComponent,
    canActivate: [AdminpageguardService]
  },

  {
    path: 'admin/adminOffice',
    component: AdminOfficeComponent,
    canActivate: [AdminpageguardService]
  },
  {
    path: 'admin/cudOffice',
    component: CudOfficeComponent,

    canActivate: [AdminpageguardService]
  },
  {
    path: 'admin/cudEmp',
    component: CudEmpComponent,
    canActivate: [AdminpageguardService]
  },
  {
    path: 'admin/editOffice/:id',
    component:EditOfficeComponent,
    canActivate: [AdminpageguardService]
  }
  ,
  {
    path: 'admin/adminAdvert',
    component: AdminAdvertComponent,
    canActivate: [AdminpageguardService]
  },
  {
    path: 'admin/editAdvert',
    component: EditAdvertComponent,
    canActivate: [AdminpageguardService]
  },
  {
    path: 'admin/editAdvert/:id',
    component: EditAdvertComponent,
    canActivate: [AdminpageguardService]
  },
  {
    path: 'admin/createAdvert',
    component: CreateAdvertComponent,
    canActivate: [AdminpageguardService]
  },
  {
    path: 'orderHistory',
    component: OrderHistoryComponent,
    canActivate: [NotauthpageguardService]

  },
  {
    path: 'admin/editEmployee/:id',
    component: EditEmployeeComponent,
    canActivate: [AdminpageguardService]
  }
];
