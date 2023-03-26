import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../../_services/token-storage.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'dashboard-index',
  templateUrl: './dashboard-index.component.html',
  styleUrls: ['./dashboard-index.component.scss']
})
export class DashboardIndexComponent implements OnInit {
  isLoggedIn = false;
  userId = '-1';
  email = 'error';

  constructor(private activatedRoute: ActivatedRoute,
              private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.userId = user.id;
      this.email = user.email;
    }
  }
}
