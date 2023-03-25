import {Product} from '../interfaces/product';
import {ApiService} from '../../_services/api.service';
import {Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Available} from '../interfaces/available';
import {environment} from '../../../environments/environment';

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
    let availables: Available[] = [];
    // await fetch(environment.storage + "/how/many/all")
    //   .then((response) => response.json())
    //   .then((data: Available[]) => availables = data);
    // await fetch(environment.storage + "/products-images")
    //   .then((response) => response.json())
    //   .then((data: ProductImage[]) => productImages = data);
    await fetch(environment.storage + '/how/many/all')
      .then((response) => response.json())
      .then((data: Available[]) => availables = data);
    this.api.getProducts()
      .subscribe(resp => {
        if (resp.body !== null) {
          for (const data of resp.body) {
            data.available = 0;
            const first = availables.find(obj => obj.product_id === data.id);
            if (first != null) {
              data.available = first.q;
            }
            products.push(data);
          }
        }
      });
    return products;
  }
}
