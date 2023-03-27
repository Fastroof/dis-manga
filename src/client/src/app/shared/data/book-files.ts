import {ApiService} from '../../_services/api.service';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {BookFile} from '../interfaces/book-file';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookFilesResolver implements Resolve<Observable<BookFile[]>> {
  constructor(private api: ApiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<BookFile[]> {
    const id = route.paramMap.get('id');
    if (id == null || !/^\+?(0|[1-9]\d*)$/.test(id)) {
      return null as any;
    }
    return this.api.getBookFilesByBookId(id);
  }
}
