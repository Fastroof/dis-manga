import {ApiService} from '../../_services/api.service';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Book} from '../interfaces/book';

@Injectable({
  providedIn: 'root'
})
export class BookResolver implements Resolve<Observable<Book>> {
  constructor(private api: ApiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Book> {
    const id = route.paramMap.get('id');
    if (id == null || !/^\+?(0|[1-9]\d*)$/.test(id)) {
      return null as any;
    }
    return this.api.getBook(id);
  }
}
