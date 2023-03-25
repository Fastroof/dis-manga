import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const TOKEN_EXPIRY = 'token-expiry';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  private static saveTokenExpiry(): void {
    window.localStorage.removeItem(TOKEN_EXPIRY);
    const now = new Date();
    window.localStorage.setItem(TOKEN_EXPIRY, String(now.getTime() + 86400000)); // 1 day 86400000
  }

  signOut(): void {
    window.localStorage.clear();
  }

  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
    TokenStorageService.saveTokenExpiry();
  }

  public getToken(): string {
    const now = new Date();
    const expiry = localStorage.getItem(TOKEN_EXPIRY);
    if (expiry != null && now.getTime() > +expiry) {
      this.signOut();
      window.location.reload();
    }
    // @ts-ignore
    return localStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    // @ts-ignore
    return JSON.parse(localStorage.getItem(USER_KEY));
  }
}
