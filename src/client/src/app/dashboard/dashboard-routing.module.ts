import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardIndexComponent } from './dashboard-index/dashboard-index.component';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import {AuthGuardService} from '../_services/auth-guard.service';
import {DashboardModeratorComponent} from './dashboard-moderator/dashboard-moderator.component';
import {ModeratorAuthGuardService} from '../_services/moderator-auth-guard.service';

const DashboardChildrenRoute: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: DashboardIndexComponent
  },
  {
    path: 'profile',
    component: DashboardProfileComponent
  },
  {
    path: 'books',
    component: DashboardProfileComponent
  },
  {
    path: 'moderate',
    component: DashboardModeratorComponent,
    canActivate: [ModeratorAuthGuardService]
  }
];

const routes: Routes = [
  {
    path: '',
    component: DashboardLayoutComponent,
    children: DashboardChildrenRoute,
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {}
