import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Book} from '../../shared/interfaces/book';

@Component({
  selector: 'book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent implements OnInit {
  book: Book | undefined;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.book = d['book'];
    });
  }
}
