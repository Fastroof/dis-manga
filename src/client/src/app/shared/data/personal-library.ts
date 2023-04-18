import {Injectable} from '@angular/core';
import {Resolve} from '@angular/router';
import {Book} from '../interfaces/book';
import {ApiService} from '../../_services/api.service';

@Injectable({
  providedIn: 'root'
})
export class PersonalLibraryResolver implements Resolve<Book[]> {
  constructor(private api: ApiService) {}

  async resolve(): Promise<Book[]> {
    return await this.getPersonalLibrary();
  }

  // tslint:disable-next-line:typedef
  async getPersonalLibrary() {
    const books: Book[] = [];
    this.api.getPersonalLibrary()
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
