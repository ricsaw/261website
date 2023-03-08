import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppComponent } from '../app.component';
import { DataTransferService } from '../data-transfer.service';
import { Product } from '../Product';
import { ProductService } from '../product.service';
import { User } from '../User';
import { UserService } from '../user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  public products!: Product[];  // TODO look at this later, I put an exclamation mark there so it
                                // ignores cases where products is undefined

  public editProduct: Product;  // Whatever product is being edited
  public deleteProduct: Product; // Whatever product is being delete

  public parent:AppComponent;

  constructor(public productService: ProductService,
              public dataTransfer: DataTransferService, public userService: UserService) { }

  ngOnInit(): void {
    this.dataTransfer.menu = this;
    this.getProducts();
  }

  /**
   * getProducts(): Attempts to get the list of products from the backend
   */
  public getProducts(): void{
    this.productService.getProducts().subscribe( //  Call getProducts from product service and wait until it gets a response
      (response: Product[]) => {  // if response is a product array, set this.products to response
        this.products = response;
      },
      (error: HttpErrorResponse) => { // if getProducts returns an error send an error message
        alert(error.message);
      }
    ); 
  }
  
  /**
   * onAddProduct(): Attempts to send the json form to the backend through product.service
   * 
   * @param addForm: A json form to convert into a product in the backend
   */
  public onAddProduct(addForm: NgForm): void{
    document.getElementById('add-product-form')?.click();
    this.productService.addProduct(addForm.value).subscribe(
      (response: Product) => {
        console.log(response);
        this.getProducts(); // Update product list
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );  // Send addProduct a json representation of the form and wait for a response
  }

  /**
   * onUpdateProduct(): Attempts to update a product through the backend
   *
   * @param product: The product to update
   */
  public onUpdateProduct(product: Product): void{
    this.productService.updateProduct(product).subscribe(
      (response: Product) => {
        console.log(response);
        this.getProducts(); // Update product list
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );  // Send addProduct a json representation of the form and wait for a response
    
    this.userService.getUsers().subscribe(
      (response: User[]) => {

        // Loop through each user in the user list
        response.forEach(user => {
          // Loop through every element of each user's cart
          user.cart.forEach(p => {
            // If the 
            if(p.id == product.id){
              user.cart[user.cart.indexOf(p)].description = product.description;
              user.cart[user.cart.indexOf(p)].name = product.name;
              user.cart[user.cart.indexOf(p)].price = product.price;
              user.cart[user.cart.indexOf(p)].imageUrl = product.imageUrl;

              this.userService.updateUser(user).subscribe(
                (result: User) => {
                  console.log(result);
                },
              );
              
              response[response.indexOf(user)] = user;
            }
          });
        });

        this.dataTransfer.home.getUsers();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  
  /**
   * onDeleteProduct(): Attempts to delete a product through the backend
   *
   * @param productId: The ID of the product to delete
   */
  public onDeleteProduct(productId: number): void{
    this.productService.deleteProduct(productId).subscribe(
      (response: void) => {
        console.log(response);
        this.getProducts(); // Update product list
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );  // Send addProduct a json representation of the form and wait for a response
    this.userService.getUsers().subscribe(
      (response: User[]) => {
          
          // Loop through each user in the user list
          response.forEach(user => {
            // Loop through every element of each user's cart
            user.cart.forEach(p => {
            //delete product from cart if it is in the cart
              if(p.id == productId){
                user.cart.splice(user.cart.indexOf(p), 1);
                this.userService.updateUser(user).subscribe(
                  (result: User) => {
                    console.log(result);
                  },
                );
                response[response.indexOf(user)] = user;

              }
            });
          });
          this.dataTransfer.home.getUsers();
        },
    );
  }

  /**
   * searchProducts():  Gets all products containing a keyword
   *
   * @param key: The keyword to search for
   *
   * Note:  this might be redundant because of the backend search function but I'm keeping it for now
   */
  public searchProducts(key: string): void {
    console.log(key);
    const results: Product[] = [];
    for (const product of this.products) {
      if (product.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || product.description.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(product);
      }
    }
    this.products = results;
    
    if(key === ''){
      this.getProducts();
    }
  }

  /**
    * productButtonManager():  Manages clicks on buttons related to products
    *
    * @param product The product to modify/add/delete
    * @param mode The which thing to do
    */
  public productButtonManager(product: Product, mode: string): void {
    const container = document.getElementById('main-container');  // gives us access to the entire div
    const button = document.createElement('button');  // Generate temp button
    button.type = 'button';     // Set the button type
    button.style.display = 'none'; // Make the button invisible
    button.setAttribute('data-toggle', 'modal') // set the button to close the modal

    // Set data target depending on mode
    // Uses '#' because they're IDs
    if(mode == 'add'){
      button.setAttribute('data-target', '#addProductModal');
    }
    else if(mode == 'edit'){
      this.editProduct = product;
      button.setAttribute('data-target', '#updateProductModal');
    }
    else if(mode == 'delete'){
      this.deleteProduct = product;
      button.setAttribute('data-target', '#deleteProductModal');
    }
  
    //  Add the button to the container if it exists
    container?.appendChild(button);
    button.click();   // Click the button and close the modal
  }

}
