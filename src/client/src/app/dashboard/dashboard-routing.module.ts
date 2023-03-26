import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardIndexComponent } from './dashboard-index/dashboard-index.component';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import {AuthGuardService} from '../_services/auth-guard.service';
import {DashboardAdminCategoriesComponent} from './dashboard-admin-categories/dashboard-admin-categories.component';
import {AdminAuthGuardService} from '../_services/admin-auth-guard.service';
import {DashboardAdminOrdersComponent} from './dashboard-admin-orders/dashboard-admin-orders.component';

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
    path: 'categories',
    component: DashboardAdminCategoriesComponent,
    canActivate: [AdminAuthGuardService]
  },
  {
    path: 'orders',
    component: DashboardAdminOrdersComponent,
    canActivate: [AdminAuthGuardService]
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
