import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {TokenStorageService} from './token-storage.service';
import {Book} from '../shared/interfaces/book';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {BookFile} from '../shared/interfaces/book-file';
import {BookComment} from '../shared/interfaces/book-comment';

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
    const body = new FormData();
    return this.http.post(environment.core + '/moderator/help-requests/'  + id + '/process', body, {responseType: 'text'});
  }

  getBookFilesByBookId(id: string): Observable<BookFile[]> {
    return this.http.get<BookFile[]>(environment.core + '/books/' + id + '/files');
  }

  getCommentsByBookId(id: string): Observable<BookComment[]> {
    return this.http.get<BookComment[]>(environment.core + '/books/' + id + '/comments');
  }

  // tslint:disable-next-line:typedef
  sendComment(id: number, text: string) {
    return this.http.post(environment.core + '/books/' + id + '/comments', text, {responseType: 'text'});
  }

  // tslint:disable-next-line:typedef
  sendHelpRequest(email: string, text: string) {
    const body = new FormData();
    body.append('email', email);
    body.append('text', text);
    return this.http.post(environment.core + '/help-request', body, {responseType: 'text'});
  }
}
