import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Address } from './Address';
import { Coupon } from './Coupon';
import { Product } from './Product';
import { User } from './User';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient){ }

    public getUser(userId: number): Observable<User>{
      return this.http.get<User>(`${this.apiServerUrl}/users/${userId}`);
    }
    public getUsers(): Observable<User[]>{
        return this.http.get<User[]>(`${this.apiServerUrl}/users`);
    }
    public getCart(userId: number): Observable<Product[]> {
        return this.http.get<Product[]>(`${this.apiServerUrl}/users/${userId}/cart`);
    }

    public addToCart(userId: number, product: Product): Observable<boolean> {
        return this.http.put<boolean>(`${this.apiServerUrl}/users/${userId}/cart/add`, product);
    }

    public removeFromCart(userId: number, id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/users/${userId}/cart/delete/${id}`);
    }

    public emptyCart(userId: number): Observable<User> {
        return this.http.delete<User>(`${this.apiServerUrl}/users/${userId}/cart/empty`);
    }

    public getTotalPriceOfCart(userId: number):Observable<number>{ 
    return this.http.get<number>(`${this.apiServerUrl}/users/${userId}/cart/total`)     
    }

    public toggleAdmin(userId: number): Observable<boolean>{
        return this.http.put<boolean>(`${this.apiServerUrl}/users/${userId}/changeAdminStatus`, null);
    }
    public addUser(user: User): Observable<User>{
        return this.http.post<User>(`${this.apiServerUrl}/users/add`, user);
    }

    public updateUser(user: User): Observable<User>{
        return this.http.put<User>(`${this.apiServerUrl}/users/edit`, user);
    }

    public updateUsers(users: User[]): Observable<boolean>{
        return this.http.put<boolean>(`$this.apiServerUrl}/users/editMultiple`, users);
    }

    public deleteUser(userId: number): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/users/delete/${userId}`);
    }

    public deleteCart(userId: number): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/users/${userId}/cart/delete`)
    }

    public applyCoupon(userId: number, coupon: Coupon): Observable<boolean>{
        return this.http.put<boolean>(`${this.apiServerUrl}/users/${userId}/cart/applyCoupon`, coupon);
    }

    public removeCoupon(userId: number): Observable<boolean>{
        return this.http.put<boolean>(`${this.apiServerUrl}/users/${userId}/cart/removeCoupon`, null);
    }

    public getCoupon(userId: number): Observable<Coupon>{
        return this.http.get<Coupon>(`${this.apiServerUrl}/users/${userId}/getCoupon`);
    }

    //add coupon to user coupon list
    public addCoupon(userId: number, coupon: Coupon): Observable<boolean>{
        return this.http.put<boolean>(`${this.apiServerUrl}/users/${userId}/addCoupon`, coupon);
    }
        
    //remove the address from the user's address list
    public removeAddress(userId: number, addressId: number): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/users/${userId}/address/delete/${addressId}`);
    }

    //get the user's address
    public getAddress(userId: number): Observable<any>{
        return this.http.get<any>(`${this.apiServerUrl}/users/${userId}/getAddress`);
    }

    //add the address to the user's address list
    public addAddress(userId: number, address: Address): Observable<Address>{
        return this.http.post<Address>(`${this.apiServerUrl}/users/${userId}/addAddress`, address);
    }

    //update the address in the user's address list
    public updateAddress(userId: number, address: any): Observable<boolean>{
        return this.http.put<boolean>(`${this.apiServerUrl}/users/${userId}/updateAddress/`, address);
    }

}
