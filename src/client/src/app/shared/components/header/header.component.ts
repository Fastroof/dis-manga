import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { menuList as staticMenuList } from '../../data/menus';
import {TokenStorageService} from '../../../_services/token-storage.service';
import {CartService} from '../../../_services/cart.service';

@Component({
  selector: 'll-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Input() topFixed: any;
  @Output() toggleSidenav = new EventEmitter();
  isScrolled: any;
  menuList: any[] = [];
  isLessThenLargeDevice: any;

  private role: string | undefined;
  isLoggedIn = false;
  isAdmin = false;
  isModerator = false;

  constructor(private breakpointObserver: BreakpointObserver,
              private tokenStorageService: TokenStorageService,
              private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.menuList = staticMenuList;
    this.breakpointObserver.observe(['(max-width: 1199px)']).subscribe(({ matches }) => {
      this.isLessThenLargeDevice = matches;
    });

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

  @HostListener('window:scroll', ['$event'])
  checkScroll(): void {
    this.isScrolled = window.pageYOffset > 15;
  }

  openCart(): void {
    this.cartService.openCart();
  }
}
