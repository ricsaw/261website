import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTransferService } from '../data-transfer.service';
import { Order } from '../Order';
import { OrderService } from '../order.service';
import { User } from '../User';
import { UserService } from '../user.service';

@Component({
  selector: 'app-order-manager',
  templateUrl: './order-manager.component.html',
  styleUrls: ['./order-manager.component.css']
})
export class OrderManagerComponent implements OnInit {
  public currUser: User;
  public admin: User;
  public adminID: number;

  constructor(public dataTransfer: DataTransferService, public orderService: OrderService, public userService: UserService) { }

  ngOnInit(): void {
    this.dataTransfer.orderManager = this;
    this.currUser = this.dataTransfer.home.currUser;
    this.adminID = 2;
    this.getUser(this.adminID);
    this.getUser(this.currUser.id);
    this.dataTransfer.home.currUser = this.currUser;
  }

  /** 
   * The inital get calls to the service to initialzie the user and admin
   * @param id the id of the user to get
   * @returns the user with the given id
   */
  public getUser(id: number): User {
    this.userService.getUser(id).subscribe(
      (response: User) => {
        this.dataTransfer.orderManager = this;
        if (response.username == "admin") {
          this.admin = response;
          if (this.dataTransfer.home.currUser.id === this.admin.id) {
            this.currUser = this.admin;
          }
        }
        else {
          this.currUser = response;
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    return null;
  }

  /**
   * Gets the total price of the order
   * @param order the order to get the prices for
   * @returns the total price of the order
   */
  public getTotalPrice(order: Order): number {
    let grandTotal = 0;
    
    // Create for each loop to get the total price of the order
    order.products.forEach(p => {
      grandTotal += p.price * p.quantity;
    });

    return grandTotal;
  }


  /**
   * Updates a user and the main admin
   * @param user the user to update
   */
  public async updateUsers(user: User): Promise<void> {
    await this.userService.updateUser(user).toPromise().then(
      (response: User) => {
        this.dataTransfer.orderManager = this;
        if (this.currUser.id === user.id) {
          this.currUser = response;
        }
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

    await this.userService.updateUser(this.admin).toPromise().then(
      (response: User) => {
        this.dataTransfer.orderManager = this;
        if (this.currUser.id === this.admin.id) {
          this.currUser = response;
        }
        console.log(response);
      }
    );

    
    return null;
  }

  /**
   * Deletes an order from the admin and the user that placed the order
   * @param order the order to delete
   */
  public async deleteOrder(order: Order): Promise<User> {
    // Filters out the order from admin's current orders
    this.admin.currentOrders = this.admin.currentOrders.filter((a: any) => a.id != order.id);

    // Updates admin
    this.userService.updateUser(this.admin).subscribe(
      (response: User) => {
        this.admin = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

    // Gets the user that placed the order and removes the order from their current orders
    await this.userService.getUser(order.userId).toPromise().then(
      (response: User) => {
        response.currentOrders = response.currentOrders.filter((a: any) => a.id != order.id);
        this.dataTransfer.orderManager = this;;
        this.updateUsers(response).then(
          () => { return response; }
        );
      }
    );
    
    return null;
  }

  /**
   * Archives an order from the admin and the user that placed the order
   * @param order the order to archive
   */
  public async archiveOrder(order: Order): Promise<void> {
    // gets the admin and removes the order from their previous orders
    await this.userService.getUser(this.adminID).toPromise().then(
      (response: User) => {
        // filters out the order from the admin's previous orders
        response.previousOrders = response.previousOrders.filter((a: any) => a.id != order.id);

        // updates the admin
        this.userService.updateUser(response).toPromise().then(
          (response: User) => {
            if (this.currUser.id === this.adminID) {
              this.currUser = response;
            }
            console.log(response);
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

    // gets the user that placed the order and removes the order from their previous orders
    await this.userService.getUser(order.userId).toPromise().then(
      (response: User) => {
        // filters out the order from the user's previous orders
        response.previousOrders = response.previousOrders.filter((a: any) => a.id != order.id);
        // updates the user
        this.userService.updateUser(response).toPromise().then(
          (response: User) => {
            if (this.currUser.id === order.userId) {
              this.currUser = response;
            }
            console.log(response);
          }
        );
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    this.dataTransfer.orderManager = this;
  }

  /**
   * Completes an order and moves it to previous order for the admin and the user that placed the order
   * @param order the order to complete
   */
  public async completeOrder(order: Order): Promise<void> {
    this.admin.previousOrders.push(order);
    this.admin.currentOrders = this.admin.currentOrders.filter((a: any) => a.id != order.id);
    
    await this.userService.getUser(order.userId).toPromise().then(
      (response: User) => {
        response.previousOrders.push(order);
        response.currentOrders = response.currentOrders.filter((a: any) => a.id != order.id);
        this.dataTransfer.orderManager = this;
        this.updateUsers(response).then(() => {
          return response;
        });
      }
    );
    return null;
  }
}
