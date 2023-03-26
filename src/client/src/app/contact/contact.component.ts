import { Component, OnInit } from '@angular/core';
import {ApiService} from '../_services/api.service';

@Component({
  selector: 'll-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {

  isSent = false;
  isError = false;
  form: any = {};

  constructor(private api: ApiService) {}

  ngOnInit(): void {
  }

  onClickSubmit(): void {
    this.isSent = true;
    this.api.sendHelpRequest(this.form.email, this.form.pib + this.form.text).subscribe(
      {
        next: value => {
          console.log(value);
        },
        error: err => {
          this.isError = true;
          console.log(err);
        }
      }
    );
  }
}
