import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTransferService } from '../data-transfer.service';
import { User } from '../User';
import { Coupon } from '../Coupon';
import { CouponService } from '../coupon.service';
import { UserService } from '../user.service';
import { AddressService } from '../address.service';
import { Address } from '../Address';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

  user: User;
  result: boolean = false;
  public coupon: Coupon[];
  public addressList: Address[] = [];
  public currentAddress: Address;

  constructor(public userService: UserService,
    public couponService: CouponService,
    public dataTransfer: DataTransferService,
    public addressService: AddressService,) { }

  ngOnInit(): void {
    this.user = this.dataTransfer.home.currUser;
    this.getCoupons();
    if(this.user.address != undefined){
      this.getDefaultAddress()
    }
  }

  public updatePassword(newInfo: User) {
    if (newInfo.username != "") {
      this.user.username = newInfo.username;
    }
    if (newInfo.password != "") {
      this.user.password = newInfo.password;
    }

    this.userService.updateUser(this.user).subscribe(
      (response: User) => {
        this.user = response;
        this.dataTransfer.home.currUser = response;
        this.result = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.result = false;
      }
    );
  }

  //get coupons
  public getCoupons() {
    this.couponService.getCoupons().subscribe( //  Call getProducts from product service and wait until it gets a response
      (response: Coupon[]) => {  // if response is a product array, set this.products to response
        this.coupon = response;
      },
      (error: HttpErrorResponse) => { // if getProducts returns an error send an error message
        alert(error.message);
      }
    );
  }

  public getDefaultAddress(): void {
    this.currentAddress = this.user.address[0];
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
    if (this.currentAddress.phone === ""){
      this.currentAddress.phone = "1234567890";
    }
  }

  onAddAddress(address: Address): void {
    if(this.currentAddress){
      if (address.address === "") {
        address.address = this.currentAddress.address;
      }
      if (address.city === "") {
        address.city = this.currentAddress.city;
      }
      if (address.state === "") {
        address.state = this.currentAddress.state;
      }
      if (address.zip === null) {
        address.zip = this.currentAddress.zip; 
      }
      if (address.phone === "") {
        address.phone = this.currentAddress.phone;
      }
    }
    
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
          this.userService.addAddress(this.user.id, response).subscribe(
            (response: Address) => {
              console.log(response);
              this.dataTransfer.home.currUser = this.user;
            }
          );
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

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

}
