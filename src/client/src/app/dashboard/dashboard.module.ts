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
import { DashboardAdminProductsComponent } from './dashboard-admin-products/dashboard-admin-products.component';
import { ProductDialogComponent } from './dashboard-admin-products/product-dialog/product-dialog.component';
import { ProductEditDialogComponent } from './dashboard-admin-products/product-edit-dialog/product-edit-dialog.component';
import { ProductDeleteDialogComponent } from './dashboard-admin-products/product-delete-dialog/product-delete-dialog.component';
import { ProductImageDialogComponent } from './dashboard-admin-products/product-image-dialog/product-image-dialog.component';
import { ProductImageEditDialogComponent } from './dashboard-admin-products/product-image-edit-dialog/product-image-edit-dialog.component';
import { ProductImageDeleteDialogComponent } from './dashboard-admin-products/product-image-delete-dialog/product-image-delete-dialog.component';
import { ProductIncomingDeleteDialogComponent } from './dashboard-admin-products/product-incoming-delete-dialog/product-incoming-delete-dialog.component';
import { ProductIncomingDialogComponent } from './dashboard-admin-products/product-incoming-dialog/product-incoming-dialog.component';
import { ProductIncomingEditDialogComponent } from './dashboard-admin-products/product-incoming-edit-dialog/product-incoming-edit-dialog.component';
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
    DashboardAdminProductsComponent,
    ProductDialogComponent,
    ProductEditDialogComponent,
    ProductDeleteDialogComponent,
    ProductImageDialogComponent,
    ProductImageEditDialogComponent,
    ProductImageDeleteDialogComponent,
    ProductIncomingDeleteDialogComponent,
    ProductIncomingDialogComponent,
    ProductIncomingEditDialogComponent,
    DashboardAdminOrdersComponent,
    OrderEditDialogComponent
  ],
  imports: [
    CommonModule, DashboardRoutingModule, SharedModule, MatMenuModule, MatExpansionModule, MatTabsModule,
    MatFormFieldModule, MatInputModule, MatTableModule, MatDialogModule, FormsModule, MatSelectModule]
})
export class DashboardModule {}
