import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {ProductCreate} from '../../shared/interfaces/product-create';
import {ProductDialogComponent} from './product-dialog/product-dialog.component';
import {ProductEditDialogComponent} from './product-edit-dialog/product-edit-dialog.component';
import {ProductDeleteDialogComponent} from './product-delete-dialog/product-delete-dialog.component';
import {environment} from '../../../environments/environment';
import {ProductImage} from '../../shared/interfaces/product-image';
import {ProductImageDialogComponent} from './product-image-dialog/product-image-dialog.component';
import {ProductImageEditDialogComponent} from './product-image-edit-dialog/product-image-edit-dialog.component';
import {ProductImageDeleteDialogComponent} from './product-image-delete-dialog/product-image-delete-dialog.component';
import {ProductIncoming} from '../../shared/interfaces/product-incoming';
import {ProductIncomingDialogComponent} from './product-incoming-dialog/product-incoming-dialog.component';
import {
  ProductIncomingEditDialogComponent
} from './product-incoming-edit-dialog/product-incoming-edit-dialog.component';
import {
  ProductIncomingDeleteDialogComponent
} from './product-incoming-delete-dialog/product-incoming-delete-dialog.component';

@Component({
  selector: 'app-dashboard-admin-products',
  templateUrl: './dashboard-admin-products.component.html',
  styleUrls: ['./dashboard-admin-products.component.scss']
})
export class DashboardAdminProductsComponent implements OnInit {

  products: MatTableDataSource<ProductCreate> = new MatTableDataSource<ProductCreate>();
  images: MatTableDataSource<ProductImage> = new MatTableDataSource<ProductImage>();
  incomings: MatTableDataSource<ProductIncoming> = new MatTableDataSource<ProductIncoming>();

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    fetch(environment.storage + '/products-without-incoming')
      .then((response) => response.json())
      .then((data: ProductCreate[]) => this.products = new MatTableDataSource<ProductCreate>(data.sort((a, b) => {
        return a.id - b.id;
      })));
    fetch(environment.imageStorage + '/products-images')
      .then((response) => response.json())
      .then((data: ProductImage[]) => this.images = new MatTableDataSource<ProductImage>(data.sort((a, b) => {
        return a.id - b.id;
      })));
    fetch(environment.storage + '/incomings')
      .then((response) => response.json())
      .then((data: ProductIncoming[]) => this.incomings = new MatTableDataSource<ProductIncoming>(data.sort((a, b) => {
        return a.id - b.id;
      })));
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.products.filter = filterValue.trim().toLowerCase();
  }

  addProduct(): void {
    const dialogRef = this.dialog.open(ProductDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  // tslint:disable-next-line:variable-name
  editProduct(id: number, category_id: string, name: string, info: string, price: number): void {
    const dialogRef = this.dialog.open(ProductEditDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {id, category_id, name, info, price}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  deleteProduct(id: number): void {
    const dialogRef = this.dialog.open(ProductDeleteDialogComponent, {
      width: '300px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  addProductImage(): void {
    const dialogRef = this.dialog.open(ProductImageDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%'
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  // tslint:disable-next-line:variable-name
  editProductImage(id: number, file: string, product_id: number): void {
    const dialogRef = this.dialog.open(ProductImageEditDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {id, file, product_id}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  deleteProductImage(id: number): void {
    const dialogRef = this.dialog.open(ProductImageDeleteDialogComponent, {
      width: '300px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  addProductIncoming(): void {
    const dialogRef = this.dialog.open(ProductIncomingDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  // tslint:disable-next-line:variable-name
  editProductIncoming(id: number, product_id: number, supplier: string, initial_price: number, quantity: number): void {
    const dialogRef = this.dialog.open(ProductIncomingEditDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {id, product_id, supplier, initial_price, quantity}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  deleteProductIncoming(id: number): void {
    const dialogRef = this.dialog.open(ProductIncomingDeleteDialogComponent, {
      width: '300px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }
}
