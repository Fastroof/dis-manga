import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {TokenStorageService} from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ModeratorAuthGuardService implements CanActivate {

  routeURL: string;

  constructor(private tokenStorageService: TokenStorageService, private router: Router) {
    this.routeURL = this.router.url;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!!this.tokenStorageService.getToken()) {
      if (this.tokenStorageService.getUser().role === 'moderator') {
        return true;
      }
    }
    this.router.navigate(['']).then(r => r);
    return false;
  }
}
