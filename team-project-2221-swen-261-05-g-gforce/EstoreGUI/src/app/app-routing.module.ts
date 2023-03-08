import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu/menu.component';
import { ManageUsersComponent } from './manage-users/manage-users.component';
import { ManageCouponComponent } from './coupon/manage-coupon.component';
import { PaymentComponent } from './payment/payment.component';
import { CheckOutComponent } from './check-out/check-out.component';
import { UserSettingsComponent } from './user-settings/user-settings.component';
import { OrderManagerComponent } from './order-manager/order-manager.component';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';

const routes: Routes = [
  {path:'', redirectTo:'menu', pathMatch:'full'},
  {path:'manageOrders', component:OrderManagerComponent},
  {path:'menu', component:MenuComponent},
  {path:'not-found', component:NotFoundPageComponent},
  {path:'userManager', component:ManageUsersComponent},
  {path: 'check-out', component: CheckOutComponent},
  {path:'payment', component:PaymentComponent},
  {path:'couponManager', component:ManageCouponComponent},
  {path:'user-settings', component:UserSettingsComponent},
  {path:'**', redirectTo:'not-found'}
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
export const routingComponents = [ManageCouponComponent, OrderManagerComponent, MenuComponent, ManageUsersComponent, ManageCouponComponent, CheckOutComponent, PaymentComponent, UserSettingsComponent];