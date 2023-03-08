import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Address } from '../Address';
import { AddressService } from '../address.service';
import { DataTransferService } from '../data-transfer.service';
import { Order } from '../Order';
import { OrderService } from '../order.service';
import { Product } from '../Product';
import { ProductService } from '../product.service';
import { User } from '../User';
import { UserService } from '../user.service';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.css']
})

export class CheckOutComponent implements OnInit {
  public products: Product[];
  registerForm!: FormGroup;
  public addressList: Address[] = [];
  public grandTotal: number = 0;
  public itemTotalPrice: number = 0;
  public currUser: User;
  public admin!: User;
  public adminID: number = 2;
  public users!: User[];
  submitted: boolean;
  public currentAddress: Address;
  booleans: boolean;

  constructor(private userService: UserService,
    public datatransfer: DataTransferService,
    public productService: ProductService,
    public addressService: AddressService,
    private formbuilder: FormBuilder,
    public orderService: OrderService) { }

  ngOnInit(): void {
    this.registerForm = this.formbuilder.group({
      firstName: ['', Validators.required]
    });

    this.currUser = this.datatransfer.home.currUser;
    this.products = this.currUser.cart;
    this.userService.getUsers().subscribe((res: User[]) => {
      this.users = res;
    })
    this.userService.getUser(this.adminID).subscribe(
      (response: User) => {
        this.admin = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

    if(this.datatransfer.home.cartItems != undefined) this.getDefaultAddress();
    
  }

  /**
   * This method will get the default address for the user
   * If the user does not have an address, it will set the default address to "Address", "City", "State", 12345, and "Phone Number"
   */
  public getDefaultAddress(): void {
    if(this.currUser.address.length != 0){
    this.currentAddress = this.currUser.address[0];
    }
    if(this.currentAddress === undefined){
    this.currentAddress.address = "Address";
    this.currentAddress.city = "City";
    this.currentAddress.state = "State";
    this.currentAddress.zip = 12345;
    this.currentAddress.phone = "Phone Number";
    this.currUser.address.push(this.currentAddress); 
    }
    console.log(this.currUser);
    console.log(this.currentAddress); 
    if(this.currentAddress.address === ""){
    this.currentAddress.address = "Address";
    }
    if (this.currentAddress.city === ""){
      this.currentAddress.city = "City";
    }
    if (this.currentAddress.state === ""){
      this.currentAddress.state = "State";
    }
    if (this.currentAddress.zip === null){
      this.currentAddress.zip = 12345;
    }
    if (this.currentAddress.phone === null){
      this.currentAddress.phone = "Phone Number";
    }
    if(this.currUser.address.length != 0){
      this.currUser.address[0] = this.currentAddress; 
    }else{
      this.currUser.address.push(this.currentAddress); 
    }
  }

  /**
   * add the item to the cart
   * @param product the product to add to the cart
   */
  addItem(product: Product) {
    this.datatransfer.home.addToCart(product);
    this.currUser = this.datatransfer.home.currUser;
    this.products = this.currUser.cart;
    
  }
  /**
   * gets the total price of the item
   * @param product the product to calculate price of
   * @returns the total price of the item
   */
  getItemTotalPrice(product: Product): number {
    //get the quantity of products in the cart
    this.itemTotalPrice = product.price * product.quantity;
    //only show 2 decimal places
    this.itemTotalPrice = Number(this.itemTotalPrice.toFixed(2));
    return this.itemTotalPrice;

  }

  /**
   * This method will get all addresses from the address list
   */
  updateAddressList(): void {
    this.addressService.getAddresses().subscribe(
      (response: Address[]) => {
        this.addressList = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


  /**
   * remove the item from the cart
   * @param id the id of the product to remove
   */
  removeItem(id: number) {
    this.datatransfer.home.removeFromCart(id);
    this.currUser = this.datatransfer.home.currUser;
    this.products = this.datatransfer.home.currUser.cart;
  }


  /**
   * removes all instances of an item from the cart and refills the inventory
   * @param id the id of the product to remove
   * @param quantity the quantity of the product to remove
   */
  removeAllItem(id: number, quantity: number) {
    this.datatransfer.home.removeMultipleFromCart(id, quantity);
    this.currUser = this.datatransfer.home.currUser;
    this.products = this.datatransfer.home.currUser.cart;
  }


  /**
   * gets the total price of all items in the cart
   * @returns the total price of all items in the cart
   */
  getTotalPrice(): number {
  
    this.grandTotal = 0;
    this.datatransfer.home.currUser.cart.forEach(product => {
      this.grandTotal += product.price * product.quantity;
    }
    );
    //only show 2 decimal places
    this.grandTotal = Number(this.grandTotal.toFixed(2));
    return this.grandTotal
  }

  /**
   * removes every item from the cart and refills the inventory
   */
  emptyCart(): void {
    this.currUser.cart.forEach(product => {
      this.datatransfer.home.removeMultipleFromCart(product.id, product.quantity);
    });

    this.currUser = this.datatransfer.home.currUser;
    this.products = this.datatransfer.home.currUser.cart;
  }

  /**
   * removes every item from the cart and does not refill the inventory
   */
  async emptyCartNoReplacement(): Promise<void> {
    await this.userService.emptyCart(this.currUser.id).toPromise().then(
      (response: User) => {
        console.log(response);
        this.currUser = response;
        this.datatransfer.home.currUser = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  /**
   * Updates the backend with the current user and admin
   */
  public async updateUsers(): Promise<void> {
    
    await this.userService.updateUser(this.currUser).toPromise().then(
      (response: User) => {
        this.currUser = response;
        this.datatransfer.home.currUser = this.currUser;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

    await this.userService.updateUser(this.admin).toPromise().then(
      (response: User) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  /**
   * gets all addresses? 
   */
  public getAddress(): void {
    this.addressService.getAddresses().subscribe(
      (response: Address[]) => {
        this.addressList = response;
        console.log(this.addressList);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


  /**
   * creates a new address
   * @param address the address to add
   */
  onAddAddress(address: Address): void {
    console.log(address);
    if (address.city != ""
      && address.state != ""
      && address.address != ""
      && address.zip != null
      && address.lastname != ""
      && address != this.currentAddress) {
      this.addressService.addAddress(address).subscribe(
        (response: Address) => {
          console.log(response);
          this.currentAddress = response;
          this.getAddress();
          this.userService.addAddress(this.currUser.id, response).subscribe(
            (response: Address) => {
              console.log(response);
              this.datatransfer.home.currUser = this.currUser;
            }
          );
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  /**
   * submits the order
   */
  public submitOrder(): void {
    let order = new Order();
    order.userId = this.currUser.id;
    order.products = this.currUser.cart;
    order.address = [];
    order.address.push(this.currentAddress);

    
    this.orderService.addOrder(order).subscribe(
      (response: Order) => {
        console.log(response);
        this.currUser.currentOrders.push(response);
        this.admin.currentOrders.push(response);
        this.updateUsers().then(() => {
          this.emptyCartNoReplacement().then(() => {
            this.updateUsers();
          });
        });
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  
  /**
   * sets submit to true?
   */
  public onSubmit(): void {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    alert('SUCCESS!!');
  }
}