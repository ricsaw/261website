import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Order } from './Order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){ }

  public getOrder(orderId: number): Observable<Order>{
    return this.http.get<Order>(`${this.apiServerUrl}/orders/${orderId}`);
  }
  public getOrders(): Observable<Order[]>{
      return this.http.get<Order[]>(`${this.apiServerUrl}/orders`);
  }
  public addOrder(order: Order): Observable<Order>{
      return this.http.post<Order>(`${this.apiServerUrl}/orders/add`, order);
  }

  public updateOrder(order: Order): Observable<Order>{
      return this.http.put<Order>(`${this.apiServerUrl}/orders/edit`, order);
  }

  public deleteOrder(orderId: number): Observable<void>{
      return this.http.delete<void>(`${this.apiServerUrl}/orders/delete/${orderId}`);
  } 
}
