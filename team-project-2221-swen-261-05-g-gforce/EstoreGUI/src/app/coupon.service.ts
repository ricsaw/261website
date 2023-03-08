
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Coupon } from './Coupon';

@Injectable({
  providedIn: 'root'
})

// NOTE: this is super important, make sure you use ` instead of ' in the url part of http commands
export class CouponService{

    coupon: Coupon = {
    id: 0,
    code: '',
    discount: 0,
    expirationDate: ''
    };
    
    createCoupon(value: any) {
        //get the id, name, and price from the form
        let couponId = value.couponId;
        let couponName = value.couponName;
        let couponDiscount = value.couponDiscount;
        let couponExpiration = value.couponExpiration;
        
        //create a new coupon object from the coupon model
        this.coupon.id = couponId;
        this.coupon.code = couponName;
        this.coupon.discount = couponDiscount;
        this.coupon.expirationDate = couponExpiration;

        //send the coupon to the backend
        this.addCoupon(this.coupon).subscribe(
            (response) => console.log(response),
            (error) => console.log(error)
        );
        

    }

    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient){ }

    //get the coupon by id
    public getCoupon(couponId: number): Observable<Coupon>{
        return this.http.get<Coupon>(`${this.apiServerUrl}/coupons/${couponId}`);
    }

    public getCoupons(): Observable<Coupon[]>{
        return this.http.get<Coupon[]>(`${this.apiServerUrl}/coupons`);
    }
    
    //add coupon to user coupon list
    public addCoupon(coupon: Coupon): Observable<Coupon>{
        return this.http.post<Coupon>(`${this.apiServerUrl}/coupons/add`, coupon);
    }

    public updateCoupon(coupon: Coupon): Observable<Coupon>{
        return this.http.put<Coupon>(`${this.apiServerUrl}/coupons/edit`, coupon);
    }

    public deleteCoupon(couponId: number): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/coupons/delete/${couponId}`);
    }

}