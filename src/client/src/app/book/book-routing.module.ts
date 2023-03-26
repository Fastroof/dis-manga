import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookDetailsComponent } from './book-details/book-details.component';
import { BookListComponent } from './book-list/book-list.component';
import {BookResolver} from '../shared/data/book';
import {BooksResolver} from '../shared/data/books';

const routes: Routes = [
  {
    path: '',
    component: BookListComponent,
    resolve: { products: BooksResolver}
  },
  {
    path: ':id',
    component: BookDetailsComponent,
    resolve: { product: BookResolver}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
