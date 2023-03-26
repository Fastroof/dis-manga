import {ApiService} from '../../_services/api.service';
import {Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Book} from '../interfaces/book';

@Injectable({
  providedIn: 'root'
})
export class BooksResolver implements Resolve<Book[]> {
  constructor(private api: ApiService) {}

  async resolve(): Promise<Book[]> {
    return await this.getBooks();
  }

  // tslint:disable-next-line:typedef
  async getBooks() {
    const books: Book[] = [];
    this.api.getBooks()
      .subscribe(resp => {
        if (resp.body !== null) {
          for (const data of resp.body) {
            books.push(data);
          }
        }
      });
    return books;
  }
}
