import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTransferService } from '../data-transfer.service';
import { User } from '../User';
import { UserService } from '../user.service';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {
  userList: User[] = [];
  
  constructor(public userService: UserService,
              public dataTransfer: DataTransferService) { }

  public updateUserList(){
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.userList = response;
      },
      (error: HttpErrorResponse) => { // if getProducts returns an error send an error message
        alert(error.message);
      }
    );
  }

  public toggleAdmin(user: User){
    user.admin = !user.admin;
    this.userService.updateUser(user).subscribe(
      (response: User) => {
        this.updateUserList();
      },
      (error: HttpErrorResponse) => { // if getProducts returns an error send an error message
        alert(error.message);
      }
    );
  }

  ngOnInit(): void {
    this.dataTransfer.userManager = this;
    this.updateUserList();
  }

  public parentCurrIsAdmin(): boolean{
    try{
      return this.dataTransfer.home.currIsAdmin();
    }
    catch{
      if(this.dataTransfer.home.currUser){
        return this.dataTransfer.home.currUser.admin;
      }
      return false;
    }
  }
}
