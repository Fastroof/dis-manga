import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
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

  getPersonalLibrary(): Observable<HttpResponse<Book[]>> {
    return this.http.get<Book[]>(environment.core + '/user/personal-library',
      {observe: 'response', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  deleteBookFromPersonalLibrary(id: number) {
    return this.http.delete(environment.core + '/user/personal-library/'  + id,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  getBook(id: string): Observable<Book> {
    return this.http.get<Book>(environment.core + '/books/' + id);
  }

  getBookFilesByBookId(id: string): Observable<BookFile[]> {
    return this.http.get<BookFile[]>(environment.core + '/books/' + id + '/files');
  }

  getCommentsByBookId(id: string): Observable<BookComment[]> {
    return this.http.get<BookComment[]>(environment.core + '/books/' + id + '/comments');
  }

  // tslint:disable-next-line:typedef
  sendComment(id: number, text: string) {
    return this.http.post(environment.core + '/books/' + id + '/comments', text,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  sendHelpRequest(email: string, text: string) {
    const body = new FormData();
    body.append('email', email);
    body.append('text', text);
    return this.http.post(environment.core + '/help-request', body,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  sendReport(id: number, text: string) {
    return this.http.post(environment.core + '/books/' + id + '/report', text,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  addToPersonalLibrary(id: number) {
    return this.http.post(environment.core + '/books/'  + id + '/personal-library', '',
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  processHelpRequest(id: number) {
    const body = new FormData();
    return this.http.post(environment.core + '/moderator/help-requests/'  + id + '/process', body,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  processReport(id: number) {
    const body = new FormData();
    return this.http.post(environment.core + '/moderator/reports/'  + id + '/process', body,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  deleteBook(id: number) {
    return this.http.delete(environment.core + '/books/'  + id,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  addBook(name: string | undefined, tagId: number | undefined, files: File[] | undefined, cover: File | undefined) {
    const body = new FormData();
    if (name !== undefined) {
      body.append('name', name);
    }
    if (tagId !== undefined) {
      body.append('tagId', tagId.toString());
    }
    if (files !== undefined) {
      for (const file of files) {
        body.append('files', file, file.name);
      }
    }
    if (cover !== undefined) {
      body.append('coverFile', cover, cover.name);
    }
    return this.http.post(environment.core + '/books', body,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }

  // tslint:disable-next-line:typedef
  editBook(id: number, name: string, tagId: number, files: File[] | undefined, cover: File | undefined) {
    const body = new FormData();
    if (name !== null) {
      body.append('name', name);
    }
    if (tagId !== null) {
      body.append('tagId', tagId.toString());
    }
    if (files !== undefined) {
      for (const file of files) {
        body.append('files', file, file.name);
      }
    }
    if (cover !== undefined) {
      body.append('coverFile', cover, cover.name);
    }
    return this.http.patch(environment.core + '/books/' + id, body,
      {responseType: 'text', headers: new HttpHeaders().set('Authorization',  `Bearer ${this.tokenStorageService.getToken()}`)});
  }
}
