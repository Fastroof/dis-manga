import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatMenuModule } from '@angular/material/menu';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardIndexComponent } from './dashboard-index/dashboard-index.component';
import { SharedModule } from '../shared/shared.module';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { DashboardAdminCategoriesComponent } from './dashboard-admin-categories/dashboard-admin-categories.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import { CategoryDialogComponent } from './dashboard-admin-categories/category-dialog/category-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import { CategoryEditDialogComponent } from './dashboard-admin-categories/category-edit-dialog/category-edit-dialog.component';
import { CategoryDeleteDialogComponent } from './dashboard-admin-categories/category-delete-dialog/category-delete-dialog.component';
import { DashboardAdminOrdersComponent } from './dashboard-admin-orders/dashboard-admin-orders.component';
import { OrderEditDialogComponent } from './dashboard-admin-orders/order-edit-dialog/order-edit-dialog.component';
import {MatSelectModule} from '@angular/material/select';

@NgModule({
  declarations: [
    DashboardLayoutComponent,
    DashboardIndexComponent,
    DashboardProfileComponent,
    DashboardAdminCategoriesComponent,
    CategoryDialogComponent,
    CategoryEditDialogComponent,
    CategoryDeleteDialogComponent,
    DashboardAdminOrdersComponent,
    OrderEditDialogComponent
  ],
  imports: [
    CommonModule, DashboardRoutingModule, SharedModule, MatMenuModule, MatExpansionModule, MatTabsModule,
    MatFormFieldModule, MatInputModule, MatTableModule, MatDialogModule, FormsModule, MatSelectModule]
})
export class DashboardModule {}
