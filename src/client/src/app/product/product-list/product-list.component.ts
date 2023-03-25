import { Component, OnInit } from '@angular/core';
import {Product} from '../../shared/interfaces/product';
import {ActivatedRoute} from '@angular/router';
import $ from 'jquery';
import {CartService} from '../../_services/cart.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'll-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  isLoaded: any;
  advanceSearchExpanded = false;
  products: Product[] = [];
  exchange: any;

  constructor(private activatedRoute: ActivatedRoute,
              private cartService: CartService) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.activatedRoute.data.subscribe(d => {
        // tslint:disable-next-line:no-string-literal
        this.products = d['products'];
      });
      fetch(environment.exchange)
        .then(r => r.text())
        .then(data => this.exchange = JSON.parse(data)[0].rate)
        .catch(() => this.exchange = 'error');

      // tslint:disable-next-line:only-arrow-functions typedef
      $(function() {
        // tslint:disable-next-line:only-arrow-functions typedef
        $('#search').on('click', function() {
          const input = $('#search-input');
          // @ts-ignore
          const value = input.val().toLowerCase();
          // @ts-ignore
          // tslint:disable-next-line:typedef
          $('.product-div').filter(function() {
            $(this).toggle($(this).find('.product-name').text().toLowerCase().indexOf(value) > -1);
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
            $('.product-div').filter(function() {
              $(this).toggle($(this).find('.product-name').text().toLowerCase().indexOf(value) > -1);
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
          $('.product-div').filter(function() {
            const value = parseFloat($(this).find('.product-price').text());
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

  // tslint:disable-next-line:typedef
  addToCart(product: Product) {
    if (product.available !== 0) {
      this.cartService.addToCart(product);
    }
  }
}
