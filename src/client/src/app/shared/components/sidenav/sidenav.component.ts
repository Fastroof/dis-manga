import { Component, OnInit } from '@angular/core';
import { menuList } from '../../data/menus';
import {TokenStorageService} from '../../../_services/token-storage.service';
import {CartService} from '../../../_services/cart.service';

@Component({
  selector: 'll-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {
  navList: any[] = [];

  private role: string | undefined;
  isLoggedIn = false;
  isAdmin = false;
  isModerator = false;

  constructor(private tokenStorageService: TokenStorageService,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.navList = menuList;

    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const role = this.tokenStorageService.getUser().role;
      this.isAdmin = role === 'admin';
      this.isModerator = role === 'moderator';
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  openCart(): void {
    this.cartService.openCart();
  }
}
