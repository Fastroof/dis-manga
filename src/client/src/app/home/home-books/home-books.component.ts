import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Book} from '../../shared/interfaces/book';

@Component({
  selector: 'home-books',
  templateUrl: './home-books.component.html',
  styleUrls: ['./home-books.component.scss']
})
export class HomeBooksComponent implements OnInit {
  books: Book[] = [];

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.books = d['books'];
    });
  }
}
