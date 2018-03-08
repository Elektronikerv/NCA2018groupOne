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
import {AdminComponent} from "./components/admin/admin.component";
import {NoPrivilegeComponent} from "./components/no-privilege/no-privilege.component";
import {VerifyEmailComponent} from "./components/verify-email/verify-email.component";
import {AdminpageguardService} from "./service/guard/adminpageguard.service";
import {EditOfficeComponent} from "./components/admin/adminOffice/editOffice/editOffice.component";
import {AdminAdvertComponent} from "./components/admin/adminAdvert/adminAdvert.component";
import {CreateEditAdvertComponent} from "./components/admin/adminAdvert/createEditAdvert/createEditAdvert.component";
import {OrderHistoryComponent} from "./components/order-history/order-history.component";
import {EditEmployeeComponent} from "./components/admin/adminEmp/editEmployee/editEmployee.component";
import {NotauthpageguardService} from "./service/guard/notauthpageguard.service";
import {UpdPasswordComponent} from "./components/home/password/updPassword.component";
import {CcagentComponent} from "./components/ccagent/ccagent.component";
import {EditOrderCcagentComponent} from "./components/ccagent/edit-order-ccagent/edit-order-ccagent.component";
import {CcagentPageGuardService} from "./service/guard/ccagentPageGuard.service";
import {ManagerPageGuardService} from "./service/guard/managerPageGuard.service";
import {CourierPageGuardService} from "./service/guard/courierPageGuard.service";
import {CourierComponent} from "./components/courier/courier.component";
import {CreateOrderComponent} from "./components/create-order/create-order.component";

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
    component: NoPrivilegeComponent
  },
  {
    path: 'verifyEmail',
    component: VerifyEmailComponent
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [NotauthpageguardService]
  },
  {
    path: 'admin',
    canActivate: [AdminpageguardService],
    children: [
      {path: 'adminEmp', component: AdminEmpComponent},
      {path: 'adminOffice', component: AdminOfficeComponent},
      {path: 'cudOffice', component: CudOfficeComponent},
      {path: 'cudEmp', component: CudEmpComponent},
      {path: 'editOffice/:id', component: EditOfficeComponent},
      {path: 'editEmployee/:id', component: EditEmployeeComponent},
      {path: 'adminAdvert', component: AdminAdvertComponent},
      {path: 'createEditAdvert', component: CreateEditAdvertComponent},
      {path: 'createEditAdvert/:id', component: CreateEditAdvertComponent}
    ]
  },
  {
    path: 'orderHistory/:id',
    component: OrderHistoryComponent,
    canActivate: [NotauthpageguardService]

  },
  {
    path: 'user/updPassword',
    component: UpdPasswordComponent,
    canActivate: [NotauthpageguardService]
  },
  {
    path: 'ccagent/orders',
    component: CcagentComponent,
    canActivate: [CcagentPageGuardService]
  },
  {
    path: 'ccagent/orders/:id',
    component: EditOrderCcagentComponent,
    canActivate: [CcagentPageGuardService]
  },

  {
    path: 'courier/orders',
    component: CourierComponent,
    canActivate: [CourierPageGuardService]
  },

  {
    path: 'createOrder',
    component: CreateOrderComponent,
    canActivate: [NotauthpageguardService]
  }
];
