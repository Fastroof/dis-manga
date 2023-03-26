import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import $ from 'jquery';
import {environment} from '../../../environments/environment';
import {Book} from '../../shared/interfaces/book';

@Component({
  selector: 'book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  isLoaded: any;
  advanceSearchExpanded = false;
  books: Book[] = [];

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.activatedRoute.data.subscribe(d => {
        // tslint:disable-next-line:no-string-literal
        this.books = d['books'];
      });
      // tslint:disable-next-line:only-arrow-functions typedef
      $(function() {
        // tslint:disable-next-line:only-arrow-functions typedef
        $('#search').on('click', function() {
          const input = $('#search-input');
          // @ts-ignore
          const value = input.val().toLowerCase();
          // @ts-ignore
          // tslint:disable-next-line:typedef
          $('.book-div').filter(function() {
            $(this).toggle($(this).find('.book-name').text().toLowerCase().indexOf(value) > -1);
          });
          input.val('');
        });

        // tslint:disable-next-line:typedef
        $('#search-input').on('keypress', function(event) {
          if (event.key === 'Enter') {
            // @ts-ignore
            const value = $(this).val().toLowerCase();
            // @ts-ignore
            // tslint:disable-next-line:typedef
            $('.book-div').filter(function() {
              $(this).toggle($(this).find('.book-name').text().toLowerCase().indexOf(value) > -1);
            });
            $(this).val('');
          }
        });

        // tslint:disable-next-line:only-arrow-functions typedef
        $('#filter-search').on('click', function() {
          const min = $('#min-price').val();
          const max = $('#max-price').val();

          // @ts-ignore
          // tslint:disable-next-line:typedef
          $('.book-div').filter(function() {
            const value = parseFloat($(this).find('.book-price').text());
            let firstCheck = false;
            if (min === '') {
              firstCheck = true;
            } else { // @ts-ignore
              if (value >= min) {
                firstCheck = true;
              }
            }
            let secondCheck = false;
            if (max === '') {
              secondCheck = true;
            } else { // @ts-ignore
              if (value <= max) {
                secondCheck = true;
              }
            }
            // @ts-ignore
            $(this).toggle(firstCheck && secondCheck);
          });
        });
      });
      this.isLoaded = true;
    }, 500);
  }
}
