import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm} from '@angular/forms';
import { AppComponent } from '../app.component';
import { DataTransferService } from '../data-transfer.service';
import { User } from '../User';
import { Coupon } from '../Coupon';
import { CouponService } from '../coupon.service';

@Component({
  selector: 'app-manage-coupon',
  templateUrl: './manage-coupon.component.html',
  styleUrls: ['./manage-coupon.component.css']
})
export class ManageCouponComponent implements OnInit {
  public coupons!: Coupon[];  // TODO look at this later, I put an exclamation mark there so it
                                // ignores cases where products is undefined
  public userList!: User[]; 

  public editCoupon: Coupon;  // Whatever product is being edited
  public deleteCoupon: Coupon; // Whatever product is being delete
  

  public parent:AppComponent;

  constructor(public couponService: CouponService,
              public dataTransfer: DataTransferService) { }

  ngOnInit(): void {
    this.dataTransfer.couponManager = this;
    this.getCoupons();
  }

  /**
   * Get Coupons
   */
    public getCoupons(): void {
        this.couponService.getCoupons().subscribe(
            (response: Coupon[]) => {
                this.coupons = response;
                console.log(this.coupons);
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }
    
  
  /**
   * onAddCoupon(): Attempts to send the json form to the backend through product.service
   * 
   * @param addForm: A json form to convert into a product in the backend
   */
    public onAddCoupon(addForm: NgForm): void {
        document.getElementById('add-coupon-form')?.click();
        this.couponService.addCoupon(addForm.value).subscribe(
            (response: Coupon) => {
                console.log(response);
                this.getCoupons();
                addForm.reset();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
                addForm.reset();
            }

        );
    }
  
  /**
   * onDeleteCoupon(): Attempts to delete a coupon through the backend
   *
   * @param couponId: The ID of the coupon to delete
   */
    public onDeleteCoupon(couponId: number): void {
        this.couponService.deleteCoupon(couponId).subscribe(
            (response: void) => {
                console.log(response);
                this.getCoupons();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }


  

}
