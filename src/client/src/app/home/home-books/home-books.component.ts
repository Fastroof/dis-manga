import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Product} from '../../shared/interfaces/product';

@Component({
  selector: 'home-books',
  templateUrl: './home-books.component.html',
  styleUrls: ['./home-books.component.scss']
})
export class HomeBooksComponent implements OnInit {
  books: Product[] = [];

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.books = d['books'];
    });
  }
}
