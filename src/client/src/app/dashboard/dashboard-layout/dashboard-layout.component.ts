import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../../_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'll-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.scss']
})
export class DashboardLayoutComponent implements OnInit {
  isLessThenLargeDevice: any;
  isAdmin = false;
  isModerator = false;

  constructor(private breakpointObserver: BreakpointObserver,
              private tokenStorageService: TokenStorageService,
              private router: Router) {}

  ngOnInit(): void {
    const role = this.tokenStorageService.getUser().role;
    this.isAdmin = role === 'admin';
    this.isModerator = role === 'moderator';

    this.breakpointObserver.observe(['(max-width: 1199px)']).subscribe(({ matches }) => {
      this.isLessThenLargeDevice = matches;
    });
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigateByUrl('/').then(r => r);
  }

}
