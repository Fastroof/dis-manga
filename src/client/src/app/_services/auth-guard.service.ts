import { Injectable } from '@angular/core';
import {TokenStorageService} from './token-storage.service';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  routeURL: string;

  constructor(private tokenStorageService: TokenStorageService, private router: Router) {
    this.routeURL = this.router.url;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!!this.tokenStorageService.getToken()) {
      return true;
    }
    this.router.navigate(['/auth/login']).then(r => r);
    return false;
  }
}
