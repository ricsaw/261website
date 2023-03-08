///
/// File: shopping-cart.service.ts
/// Author: James De Luca
/// Description: The TypeScript service file for the shopping cart.
///

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from './Product';
import { ShoppingCart } from './ShoppingCart';

@Injectable({
  providedIn: 'root'
})

export class ShoppingCartService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient){ }

    // Shopping cart methods
    public getCart(cartId: number): Observable<ShoppingCart>
    {
        return this.http.get<ShoppingCart>(`${this.apiServerUrl}/shoppingCarts/${cartId}`);
    }
    public getCarts(): Observable<ShoppingCart[]>
    {
        return this.http.get<ShoppingCart[]>(`${this.apiServerUrl}/shoppingCarts`);
    }
    public getCartItems(cartId: number): Observable<Product[]>
    {
        return this.http.get<Product[]>(`${this.apiServerUrl}/shop-carts/${cartId}/items`);
    }
    
    public addCart(cart: ShoppingCart): Observable<ShoppingCart> 
    {
        return this.http.post<ShoppingCart>(`${this.apiServerUrl}/shoppingCarts/add`, cart);
    }
    public addCartItem(cartId: number, product: Product): Observable<Product>
    {
        return this.http.post<Product>(`${this.apiServerUrl}/shoppingCarts/${cartId}/add-item`, product);
    }

    public deleteCart(cartId: number): Observable<void>
    {
        return this.http.delete<void>(`${this.apiServerUrl}/shoppingCarts/delete/${cartId}`);
    }
    public deleteCartItem(cartId: number, productId: number): Observable<void> 
    {
        return this.http.delete<void>(`${this.apiServerUrl}/shoppingCarts/${cartId}/delete-item/${productId}`);
    }


}