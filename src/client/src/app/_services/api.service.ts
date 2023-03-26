import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {TokenStorageService} from './token-storage.service';
import {Book} from '../shared/interfaces/book';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {}

  getBooks(): Observable<HttpResponse<Book[]>> {
    return this.http.get<Book[]>(environment.core + '/books', {observe: 'response'});
  }

  getBook(id: string): Observable<Book> {
    return this.http.get<Book>(environment.core + '/books/' + id);
  }

  // tslint:disable-next-line:typedef
  processHelpRequest(id: number) {
    return this.http.post(environment.core + '/moderator/help-requests/'  + id + '/process', {responseType: 'text'});
  }
}
