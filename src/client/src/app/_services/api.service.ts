import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Product } from '../shared/interfaces/product';
import {Observable} from 'rxjs';
import {ProductImage} from '../shared/interfaces/product-image';
import {OrderItemForOrder} from '../shared/interfaces/order-item-for-order';
import {environment} from '../../environments/environment';
import {TokenStorageService} from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {}

  getProducts(): Observable<HttpResponse<Product[]>> {
    return this.http.get<Product[]>(environment.storage + '/products-without-incoming', {observe: 'response'});
  }

  getProduct(id: string): Observable<Product> {
    return this.http.get<Product>(environment.storage + '/product-with-image/' + id);
  }

  getProductsImages(): Observable<HttpResponse<ProductImage[]>> {
    return this.http.get<ProductImage[]>(environment.storage + '/products-images', {observe: 'response'});
  }

  // tslint:disable-next-line:typedef variable-name
  createOrder(user_id: string | undefined | null,
              address: string,
              email: string,
              payment: string,
              // tslint:disable-next-line:variable-name
              payment_type: string,
              phone: string,
              username: string,
              orderItemCreateRequests: OrderItemForOrder[]) {
    const status = 'pending';
    const type = 'INTERNET';
    if (user_id === undefined) {
      user_id = null;
    }
    const body = {
      status,
      type,
      user_id,
      address,
      email,
      payment,
      payment_type,
      phone,
      username,
      order_items: orderItemCreateRequests
    };
    return this.http.post(environment.order + '/order', body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  addCategory(name: string) {
    const body = new FormData();
    body.append('name', name);
    return this.http.post(environment.storage + '/category', body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  editCategory(id: number, name: string) {
    const body = new FormData();
    body.append('name', name);
    return this.http.put(environment.storage + '/category/' + id, body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  deleteCategory(id: number) {
    return this.http.delete(environment.storage + '/category/' + id, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef variable-name
  addProduct(category_id: number | null, name: string, info: string, price: number) {
    const body = new FormData();
    if (category_id != null) {
      body.append('category_id', category_id.toString());
    }
    body.append('name', name);
    body.append('info', info);
    body.append('price', price.toString());
    return this.http.post(environment.storage + '/product', body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef variable-name
  editProduct(id: number, category_id: number | null, name: string, info: string, price: number) {
    const body = new FormData();
    if (category_id != null) {
      body.append('category_id', category_id.toString());
    }
    body.append('name', name);
    body.append('info', info);
    body.append('price', price.toString());
    return this.http.put(environment.storage + '/product/' + id, body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  deleteProduct(id: number) {
    return this.http.delete(environment.storage + '/product/' + id, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  uploadProductImage(id: number, file: File) {
    const body = new FormData();
    body.append('file', file);
    body.append('product_id', id.toString());
    return this.http.post(environment.imageStorage + '/upload', body);
  }

  // tslint:disable-next-line:typedef variable-name
  editProductImage(id: number, product_id: number) {
    const body = new FormData();
    body.append('product_id', product_id.toString());
    return this.http.put(environment.imageStorage + '/' + id, body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  deleteProductImage(id: number) {
    return this.http.delete(environment.imageStorage + '/delete/' + id, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef variable-name
  addProductIncoming(product_id: number, supplier: string, initial_price: number, quantity: number) {
    const body = new FormData();
    body.append('product_id', product_id.toString());
    body.append('supplier', supplier);
    body.append('initial_price', initial_price.toString());
    body.append('quantity', quantity.toString());
    return this.http.post(environment.storage + '/incoming', body, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  updateProductIncoming(id: number, quantity: number) {
    const body = new FormData();
    body.append('quantity', quantity.toString());

    return this.http.put(environment.storage + '/incoming/' + id, body,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization', this.tokenStorageService.getToken())});
  }

  // tslint:disable-next-line:typedef
  deleteProductIncoming(id: number) {
    return this.http.delete(environment.storage + '/incoming/' + id, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  updateOrderStatus(id: number, status: string) {
    const body = new FormData();
    body.append('status', status);
    return this.http.put(environment.order + '/order/' + id, body, {responseType: 'text'});
  }
}
