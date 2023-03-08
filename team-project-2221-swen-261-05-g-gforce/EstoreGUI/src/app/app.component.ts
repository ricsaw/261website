import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Address } from './Address';
import { Coupon } from './Coupon';
import { CouponService } from './coupon.service';
import { ManageCouponComponent } from './coupon/manage-coupon.component';
import { DataTransferService } from './data-transfer.service';
import { MenuComponent } from './menu/menu.component';
import { Product } from './Product';
import { ProductService } from './product.service';
import { User } from './User';
import { UserService } from './user.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'RARAJ';

  public users!: User[];
  public currUser: User;
  public editUser: User;
  public deleteUser: User;
  public loginIssue: string;
  public fourZeroFour: boolean = false;
  public dark: boolean = false; 
  
  private loginStatus: boolean = false;
  public cartItems!: Product[]; // List of items in current shopping cart
  public menu:MenuComponent;
  public coupon: Coupon[]; //list of coupons
  public invalidUser: boolean = false;
  public couponManager: ManageCouponComponent;

  public deleteCoupon: Coupon;
  public editCoupon: Coupon;

  constructor(
    public userService: UserService,
    public productService: ProductService,
    public couponService: CouponService,
    public dataTransfer: DataTransferService,
    public router: Router
    ){}
  
 
  ngOnInit(): void {
    if(localStorage.getItem('currentuser') && localStorage.getItem('currentuser') != "null" ){
      this.currUser = JSON.parse(localStorage.getItem('currentuser'));
      this.dataTransfer.home = this;
      this.loginStatus = true;
    }
      this.initDarkMode();
      this.getUsers();
      this.getCoupons();
      this.getCartItems();
      if(this.currUser != null) {
      this.getAddress();
    }
    
  }

  public initDarkMode(): void {
    if(this.currUser && this.currUser.darkMode) {
        document.documentElement.style.setProperty(`--${'dark-5'}`, '#0A0903' + ''); //suffix may be px or ''
        document.documentElement.style.setProperty(`--${'dark-4'}`, '#393C41' + '');
        document.documentElement.style.setProperty(`--${'dark-3p5'}`, '#555A62' + '');
        document.documentElement.style.setProperty(`--${'dark-3'}`, '#686E78' + '');
        document.documentElement.style.setProperty(`--${'dark-2'}`, '#1d1d1d' + ''); //Background color
        document.documentElement.style.setProperty(`--${'dark-1'}`, '#D3D5D9' + ''); //Text color
        document.documentElement.style.setProperty(`--${'accent-dark'}`, '#514A98' + ''); //Cart icon button, dropdown buttons (highlighted)
        document.documentElement.style.setProperty(`--${'accent-light'}`, '#9882D0' + ''); //Cart icon button, dropdown buttons
    }
    else {
      document.documentElement.style.setProperty(`--${'dark-5'}`, '#3C3744' + ''); //suffix may be px or ''
      document.documentElement.style.setProperty(`--${'dark-4'}`, '#444444' + '');
      document.documentElement.style.setProperty(`--${'dark-3p5'}`, '#DADADA' + '');
      document.documentElement.style.setProperty(`--${'dark-3'}`, '#F3F3F3' + '');
      document.documentElement.style.setProperty(`--${'dark-2'}`, '#ffffff' + ''); //Background color
      document.documentElement.style.setProperty(`--${'dark-1'}`, '#000000' + ''); //Text color
      document.documentElement.style.setProperty(`--${'accent-dark'}`, '#090C9B' + ''); //Cart icon button, dropdown buttons (highlighted)
      document.documentElement.style.setProperty(`--${'accent-light'}`, '#156af4' + ''); //Cart icon button, dropdown buttons
    }
  }
      
  public toggleDarkMode(): void {
    if(this.currUser.darkMode){
      this.currUser.darkMode = false;
      document.documentElement.style.setProperty(`--${'dark-5'}`, '#3C3744' + ''); //suffix may be px or ''
      document.documentElement.style.setProperty(`--${'dark-4'}`, '#444444' + '');
      document.documentElement.style.setProperty(`--${'dark-3p5'}`, '#DADADA' + '');
      document.documentElement.style.setProperty(`--${'dark-3'}`, '#F3F3F3' + '');
      document.documentElement.style.setProperty(`--${'dark-2'}`, '#ffffff' + ''); //Background color
      document.documentElement.style.setProperty(`--${'dark-1'}`, '#000000' + ''); //Text color
      document.documentElement.style.setProperty(`--${'accent-dark'}`, '#090C9B' + ''); //Cart icon button, dropdown buttons (highlighted)
      document.documentElement.style.setProperty(`--${'accent-light'}`, '#156af4' + ''); //Cart icon button, dropdown buttons

    }
    else{
      document.documentElement.style.setProperty(`--${'dark-5'}`, '#0A0903' + ''); //suffix may be px or ''
      document.documentElement.style.setProperty(`--${'dark-4'}`, '#393C41' + ''); 
      document.documentElement.style.setProperty(`--${'dark-3p5'}`, '#555A62' + ''); 
      document.documentElement.style.setProperty(`--${'dark-3'}`, '#686E78' + '');
      document.documentElement.style.setProperty(`--${'dark-2'}`, '#1d1d1d' + ''); //Background color
      document.documentElement.style.setProperty(`--${'dark-1'}`, '#D3D5D9' + ''); //Text color
      document.documentElement.style.setProperty(`--${'accent-dark'}`, '#514A98' + ''); //Cart icon button, dropdown buttons (highlighted)
      document.documentElement.style.setProperty(`--${'accent-light'}`, '#9882D0' + ''); //Cart icon button, dropdown buttons
      this.currUser.darkMode = true;
    }

    this.userService.updateUser(this.currUser).subscribe( {
      next: (response: User) => {
        this.getUsers();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }
  
  /**
    * Get list of products in current user's shopping cart.
    */
  public getCartItems(): void {
    this.getUsers();
    if(this.users){
      this.currUser = this.users.find((u) => u.id === this.currUser.id);
      this.cartItems = this.currUser.cart;
    }
  }
 
  public getFromCart(id: number): Product{
    return this.cartItems.find((p) => p.id === id);
  }

  /**
    * Add product to cart.
    */
  public addToCart(product: Product): void {
    this.userService.addToCart(this.currUser.id, product).subscribe( {
      next: (response: boolean) => {
        this.getCartItems();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });

    this.productService.getProduct(product.id).subscribe( {
      next: (response: Product) => {
        response.quantity -= 1;
        if(response.quantity <= 0) response.quantity = 0;
        this.productService.updateProduct(response).subscribe( {
          next: (updateResponse: Product) => {
            this.dataTransfer.menu.getProducts();
          }, 
          error: (error: HttpErrorResponse) => {
            alert(error.message);
          }
        });
      }, 
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });

  }

  /**
  * Remove product from cart.
  */
  public removeFromCart(id: number): void {
    this.userService.removeFromCart(this.currUser.id, id).subscribe( {
      next: (response: void) => {
        this.getCartItems();
        this.dataTransfer.home = this;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });

    this.productService.getProduct(id).subscribe( {
      next: (response: Product) => {
        response.quantity += 1;
        this.productService.updateProduct(response).subscribe( {
          next: (updateResponse: Product) => {
            this.dataTransfer.menu.getProducts();
          }, 
          error: (error: HttpErrorResponse) => {
            alert(error.message);
          }
        });
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  public removeMultipleFromCart(id: number, quantity: number): void {
    for(let i = 0; i < quantity; i++){
      this.userService.removeFromCart(this.currUser.id, id).subscribe( {
        next: (response: void) => {
          this.getCartItems();
          this.dataTransfer.home = this;
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      });
    }

    this.productService.getProduct(id).subscribe( {
      next: (response: Product) => {
        response.quantity += quantity;
        this.productService.updateProduct(response).subscribe( {
          next: (updateResponse: Product) => {
            this.dataTransfer.menu.getProducts();
          }, 
          error: (error: HttpErrorResponse) => {
            alert(error.message);
          }
        });
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }
  /**
  * Remove product from cart without returning it to inventory.
  */
  public removeFromCartNoReplacment(id: number): void {
    this.userService.removeFromCart(this.currUser.id, id).subscribe( {
      next: (response: void) => {
        this.getCartItems();
        this.dataTransfer.home = this;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  /**
   * add coupon to the users coupon list
   * @param coupon 
   * @param userId
   * @returns
   * @memberof AppComponent
   */
  public addCoupon(coupon: Coupon, userId: number): void {
    this.userService.addCoupon(userId, coupon).subscribe({
      next: (response: boolean) => {
        this.getUsers();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }
  
  /**
   * getCartTotal(): Gets the total price of all items in the cart
   * @returns the total price of all items in the cart
   */
  public getCartTotal(): number{
    let total: number = 0;
    for (let item of this.cartItems) {
      total += item.price;
    }
    return total;
  }

  /**
  * currIsAdmin(): checks if the current user is an admin
  * @returns true if user is admin
  */
  public currIsAdmin(): boolean{
    this.dataTransfer.home = this;
    if(this.currUser == null) return false;
    return this.currUser.admin;
  }

  /**
  * loggedIn():  Checks if a user is logged in
  * @returns true if a user is logged in
  */
  public loggedIn(): boolean{
    this.dataTransfer.home = this;
    return this.loginStatus;
  }

  /**
  * logout():  Logs the current user out
  */
  public logout(): void{
    this.currUser = null;
    this.loginStatus = false;
    localStorage.setItem('currentuser', null);
    this.initDarkMode()
  }

  /**
  * getUsers():  Attempts to get the user list from the backend
  */
  public getUsers() {
    this.userService.getUsers().subscribe( { //  Call getUsers from user service and wait until it gets a response
      next: (response: User[]) => { // If response is a user array, set this.users to response
        this.users = response;
      },
      error: (error: HttpErrorResponse) => { // if getUsers returns an error, send an error message
        alert(error.message);
      }
    });

    if(this.currUser){
      this.userService.getUser(this.currUser.id).subscribe( {
        next: (response: User) => {
          this.currUser = response;
          localStorage.setItem('currentuser', JSON.stringify(response));
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      });
    }
    else if(localStorage.getItem('currentuser')){
      this.currUser = JSON.parse(localStorage.getItem('currentuser'));
    }
  }
  
  /**
   * onUpdateUser(): Attempts to update a user through the backend
   *
   * @param user: The user to update
   */
  public onUpdateUser(user: User): void{
    user.id = this.editUser.id;
    if(this.currIsAdmin()){
      user.cart = this.editUser.cart;
    }
    this.userService.updateUser(user).subscribe( {
      next: (response: User) => {
        console.log(response);
        this.getUsers(); // Update product list
        this.dataTransfer.userManager.updateUserList();
        this.editUser = new User();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  /**
   * onDeleteUser(): Attempts to delete a user through the backend
   *
   * @param userId: The ID of the user to delete
   */
  public onDeleteUser(userId: number): void{
    this.userService.deleteUser(userId).subscribe( {
      next: (response: void) => {
        console.log(response);
        this.getUsers(); // Update product list
        this.dataTransfer.userManager.updateUserList();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }
  /**
  * onNewUser(): Attempts to add a new user to the backend

  * @param addForm: The json form to map to a user
  */
  public onNewUser(addForm: NgForm): void{
    this.userService.addUser(addForm.value).subscribe( {
      next: (response: User) => {
        document.getElementById('add-user-form')?.click();
        
        console.log(response);
        this.getUsers(); // Update product list
        this.invalidUser = false;
        this.loginIssue = "";
      },
      error: (error: HttpErrorResponse) => {
        this.loginIssue = "Username already exists!";
        this.invalidUser = true;
      }
    }); // Send addProduct a json representation of the form and wait for a response
  }

  /**
  * searchUsers(): Searches the list of users for a username containing a keyword
  *
  * @param key: The key to search for
  */
  public searchUsers(key: string): void {
    console.log(key);
    
    this.getUsers();
    const results: User[] = [];
    for (const user of this.users) {
      if (user.username.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(user);
      }
    }
    this.users = results;
    if (results.length === 0 || !key) {
      this.getUsers();
    }
  }

  /**
    * onUserLogin(): Attempts to log a user in
    *
    * @param userForm: The json to map to a user and log in 
    */
  public onUserLogin(userForm: NgForm): void {

    // Check to see if the user exists
    this.searchUsers(userForm.value.username);

    if (this.users.length > 0) {
      // Check if a user containing the username has correct login info 
      for (let user of this.users) {
        // If the user exists and the password is correct
        if (user.username == userForm.value.username && user.password == userForm.value.password) {
          this.currUser = user;
          this.loginStatus = true;
          localStorage.setItem('currentuser', JSON.stringify(this.currUser));
          this.loginIssue = "";
          document.getElementById('loginClose')?.click();
          this.invalidUser = false;
          this.loginIssue = "";
          this.initDarkMode();
          this.getUsers();
          return;
        }
      }
    }
    
    if(this.loginStatus === false){
      if (this.users.length > 0) {
        this.invalidUser = true;
        this.loginIssue = "Invalid username or password";
      }
      else {
        this.invalidUser = true;
        this.loginIssue = "User does not exist";
      }
    } 
  }

  //get coupons
  public getCoupons(){
    this.couponService.getCoupons().subscribe( {
      next: (response: Coupon[]) => {
        this.coupon = response;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  
  /**
   * Get list of address in current user.
   */
   public getAddress(): void {
    this.userService.getAddress(this.currUser.id).subscribe( {
      next: (response: User) => {
        this.currUser.address = response.address;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  /**
   * add coupon to the users coupon list
   * @param address 
   * @param userId
   * @returns
   * @memberof AppComponent
   */
   public addAddress(address: Address, userId: number): void {
    this.userService.addAddress(userId, address).subscribe( {
      next: () => {
        this.getUsers();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }



  /**
  * userButtonManager(): Manages button clicks from user-related buttons
  *
  * @param user The user to modify/add/remove
  * @param mode What to do to the user
  */
  public userButtonManager(user: User, mode: string): void {
    //document.getElementById('login-close').click();
    // this.loginStatus = true;
    // Uncomment this to test logged in mode ^^^
    
    const container = document.getElementById('login-container');  // gives us access to the entire div
    const button = document.createElement('button');  // Generate temp button
    button.type = 'button';     // Set the button type
    button.style.display = 'none'; // Make the button invisible
    button.setAttribute('data-toggle', 'modal') // Set the button to close a modal when clicked

    // Set data target depending on mode
    // Uses '#' because they're IDs
    if(mode == 'login'){
      this.invalidUser = false;
      button.setAttribute('data-target', '#loginScreenModal');
    }
    else if(mode == 'logout'){
      this.logout();
    }
    else if(mode == 'newUser'){
      this.invalidUser = false;
      button.setAttribute('data-target', '#newUserModal');
    }
    else if(mode == 'edit'){
      this.editUser = user;
      button.setAttribute('data-target', '#editUserModal');
    }
    else if(mode == 'delete'){
      this.deleteUser = user;
      button.setAttribute('data-target', '#deleteUserModal')
    }
    
    //  Add the button to the container if it exists
    container?.appendChild(button);
    button.click();   // Click the button and close the modal
  }
  
  
}
