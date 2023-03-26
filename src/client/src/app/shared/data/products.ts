import {Product} from '../interfaces/product';
import {ApiService} from '../../_services/api.service';
import {Resolve} from '@angular/router';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProductsResolver implements Resolve<Product[]> {
  constructor(private api: ApiService) {}

  async resolve(): Promise<Product[]> {
    return await this.getProducts();
  }

  // tslint:disable-next-line:typedef
  async getProducts() {
    const products: Product[] = [];
    this.api.getProducts()
      .subscribe(resp => {
        if (resp.body !== null) {
          for (const data of resp.body) {
            products.push(data);
          }
        }
      });
    return products;
  }
}
