import { Injectable } from '@angular/core';
import { AppComponent } from './app.component';
import { CheckOutComponent } from './check-out/check-out.component';
import { ManageCouponComponent } from './coupon/manage-coupon.component';
import { ManageUsersComponent } from './manage-users/manage-users.component';
import { MenuComponent } from './menu/menu.component';
import { OrderManagerComponent } from './order-manager/order-manager.component';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';

@Injectable({
  providedIn: 'root'
})
export class DataTransferService {
  public home:AppComponent;
  public menu:MenuComponent;
  public userManager:ManageUsersComponent;
  public checkOut:CheckOutComponent;
  public orderManager:OrderManagerComponent;
  public NotFoundPageComponent:NotFoundPageComponent;
  public couponManager:ManageCouponComponent;
}
