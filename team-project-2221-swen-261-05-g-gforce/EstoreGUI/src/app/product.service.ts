/**
  File: product.service.ts
  Author: Robert Tetreault
  Description:  sends http requests for products so the backend can change things
*/
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from './Product';

@Injectable({
  providedIn: 'root'
})

// NOTE: this is super important, make sure you use ` instead of ' in the url part of http commands
export class ProductService{
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient){ }

    public getProduct(productId: number): Observable<Product>{
      return this.http.get<Product>(`${this.apiServerUrl}/products/${productId}`);
    }
    public getProducts(): Observable<Product[]>{
        return this.http.get<Product[]>(`${this.apiServerUrl}/products`);
    }
    public addProduct(product: Product): Observable<Product>{
        return this.http.post<Product>(`${this.apiServerUrl}/products/add`, product);
    }

    public updateProduct(product: Product): Observable<Product>{
        return this.http.put<Product>(`${this.apiServerUrl}/products/edit`, product);
    }

    public deleteProduct(productId: number): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/products/delete/${productId}`);
    }
}