import { Component, OnInit } from '@angular/core';
import { DataTransferService } from '../data-transfer.service';

@Component({
  selector: 'app-not-found-page',
  templateUrl: './not-found-page.component.html',
  styleUrls: ['./not-found-page.component.css']
})
export class NotFoundPageComponent implements OnInit {

  constructor( public datatransfer : DataTransferService) { }

  setFourZeroFourFalse(){
    this.datatransfer.home.fourZeroFour = false;
  }


  ngOnInit(): void {
    this.datatransfer.home.fourZeroFour = true;
  }

}
