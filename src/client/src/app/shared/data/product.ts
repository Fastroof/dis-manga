import {Product} from '../interfaces/product';
import {ApiService} from '../../_services/api.service';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductResolver implements Resolve<Observable<Product>> {
  constructor(private api: ApiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Product> {
    const id = route.paramMap.get('id');
    if (id == null || !/^\+?(0|[1-9]\d*)$/.test(id)) {
      return null as any;
    }
    return this.api.getProduct(id);
  }
}
