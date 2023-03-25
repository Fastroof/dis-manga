import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Product} from '../../shared/interfaces/product';
import {CartService} from '../../_services/cart.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'll-home-products',
  templateUrl: './home-products.component.html',
  styleUrls: ['./home-products.component.scss']
})
export class HomeProductsComponent implements OnInit {
  products: Product[] = [];
  exchange: any;

  constructor(private activatedRoute: ActivatedRoute,
              private cartService: CartService) {}

  ngOnInit(): void {
    fetch(environment.exchange)
      .then(r => r.text())
      .then(data => this.exchange = JSON.parse(data)[0].rate)
      .catch(() => this.exchange = 'error');
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.products = d['products'];
    });
  }

  // tslint:disable-next-line:typedef
  addToCart(product: Product) {
    if (product.available !== 0) {
      this.cartService.addToCart(product);
    }
  }
}
