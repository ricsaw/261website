import { Component, OnInit } from '@angular/core';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import { environment } from 'src/environments/environment';
import { DataTransferService } from '../data-transfer.service';
import { User } from '../User';
import { UserService } from '../user.service';
import {Product} from '../Product';
import { ProductService } from '../product.service';
import { Coupon } from '../Coupon';
import { CouponService } from '../coupon.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  cartTotal! : any
  couponCode: string = '';
  date: string = '';
  public payPalConfig?: IPayPalConfig;
  showSuccess!: any;
  isExpired: boolean = false;
  isSuccess: boolean = false;
  public products: Product[];
  public currUser: User;
  public coupon: Coupon[]

  constructor(private userService: UserService, private datatransfer : DataTransferService, private productService : ProductService, private couponService: CouponService ) { }


  // public getDateInUS(): string{
  //   let date = new Date();
  //   let month = date.getMonth() + 1;
  //   let day = date.getDay();
  //   let year = date.getFullYear();
  //   return month + '/' + day + '/' + year;
  // }

  public applyCoupon(): number{
    //initialize coupon 
    this.isExpired = false;
    this.isSuccess = false;
    this.date = new Date().toLocaleDateString();
    this.coupon = this.currUser.coupon;
    this.cartTotal = 0;

    // Create for each loop to get the total price of the cart
    this.products.forEach(p => {
      this.cartTotal += p.price * p.quantity;
    });
    
    //get all coupons from the database
    this.couponService.getCoupons().subscribe((data: any) => {
      this.coupon = data;
      //check if the coupon code is valid
      for (let cpn of this.coupon) {
        if(cpn.code == this.couponCode){
          //if the coupon is valid, apply the discount, expiration date must be in the format of MM/DD/YYYY
          if(cpn.expirationDate >= this.date){
            this.cartTotal = this.cartTotal * (1 - cpn.discount);
            this.cartTotal = this.cartTotal.toFixed(2);
            this.isSuccess = true;
            return this.cartTotal;
          }//if the coupon is expired, return the original cart total 
          else{
            this.isExpired = true;
            return this.cartTotal;
          }
        }
      }
    })
    console.log(this.cartTotal);  
    return this.cartTotal;
  }

  ngOnInit(): void {

    this.currUser = this.datatransfer.home.currUser;
    this.products = this.currUser.cart; 
    this.coupon = this.currUser.coupon;

    this.initConfig();  
    //check for updated cart total
    console.log(this.cartTotal);
    this.applyCoupon();
    

  }


  private initConfig(): void {
    this.payPalConfig = {
    currency: 'USD',
    clientId: `${environment.Client_ID}`,
    createOrderOnClient: (data) => <ICreateOrderRequest>{
      intent: 'CAPTURE',
      purchase_units: [
        {
          amount: {
            currency_code: 'USD',
            value: `${this.cartTotal}`,
            breakdown: {
              item_total: {
                currency_code: 'USD',
                value: `${this.cartTotal}`
              }
            }
          },
          items: [
            {
              name: 'Enterprise Subscription',
              quantity: '1',
              category: 'DIGITAL_GOODS',
              unit_amount: {
                currency_code: 'USD',
                value: `${this.cartTotal}`,
              },
            }
          ]
        }
      ]
    },
    advanced: {
      commit: 'true'
    },
    style: {
      label: 'paypal',
      layout: 'vertical'
    },
    onApprove: (data, actions) => {
      console.log('onApprove - transaction was approved, but not authorized', data, actions);
      actions.order.get().then((details: any) => {
        console.log('onApprove - you can get full order details inside onApprove: ', details);
      });
    },
    onClientAuthorization: (data) => {
      console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
      this.showSuccess = true;
    },
    onCancel: (data, actions) => {
      console.log('OnCancel', data, actions);
    },
    onError: err => {
      console.log('OnError', err);
    },
    onClick: (data, actions) => {
      console.log('onClick', data, actions);
    },
  };

}


}