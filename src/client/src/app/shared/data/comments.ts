import {ApiService} from '../../_services/api.service';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BookComment} from '../interfaces/book-comment';

@Injectable({
  providedIn: 'root'
})
export class CommentsResolver implements Resolve<Observable<BookComment[]>> {
  constructor(private api: ApiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<BookComment[]> {
    const id = route.paramMap.get('id');
    if (id == null || !/^\+?(0|[1-9]\d*)$/.test(id)) {
      return null as any;
    }
    return this.api.getCommentsByBookId(id);
  }
}
