import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { SharedModule } from '../shared/shared.module';
import {authInterceptorProviders} from '../_helpers/auth.interceptor';
import {FormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import { InfoComponent } from './info/info.component';


@NgModule({
  declarations: [LoginComponent, SignupComponent, InfoComponent],
  imports: [
    CommonModule,
    AuthRoutingModule,
    SharedModule,
    FormsModule,
    MatInputModule
  ],
  providers: [authInterceptorProviders]
})
export class AuthModule { }
