import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  AUTH_API = environment.security;
  REDIRECT_URL = environment.redirect_uri;

  constructor(private http: HttpClient) { }

  login(credentials: any): Observable<any> {
    return this.http.post(this.AUTH_API + '/login', {
      email: credentials.email,
      password: credentials.password
    }, httpOptions);
  }

  loginGoogle(): Observable<any> {
    return this.http.post(this.REDIRECT_URL + '/callback', httpOptions);
  }

  register(user: any): Observable<any> {
    return this.http.post(this.AUTH_API + '/register', {
      name: user.name,
      email: user.email,
      password: user.password
    }, httpOptions);
  }
}
