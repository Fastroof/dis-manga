import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { ProductListComponent } from './product-list/product-list.component';
import {ProductResolver} from '../shared/data/product';
import {ProductsResolver} from '../shared/data/products';

const routes: Routes = [
  {
    path: '',
    component: ProductListComponent,
    resolve: { products: ProductsResolver}
  },
  {
    path: ':id',
    component: ProductDetailsComponent,
    resolve: { product: ProductResolver}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
