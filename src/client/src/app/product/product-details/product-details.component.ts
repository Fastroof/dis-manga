import { Component, OnInit } from '@angular/core';
import {Product} from '../../shared/interfaces/product';
import {ActivatedRoute} from '@angular/router';
import {GalleryItem, ImageItem} from 'ng-gallery';
import {CartService} from '../../_services/cart.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'll-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {
  exchange: any;
  product: Product | undefined;
  images: GalleryItem[] = [];
  available = 0;

  constructor(private activatedRoute: ActivatedRoute,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.product = d['product'];
    });
    fetch(environment.storage + '/how/many/' + this.product?.id)
      .then((response) => response.json())
      .then((data: any) => this.available = data.q);
    fetch(environment.exchange)
      .then(r => r.text())
      .then(data => this.exchange = JSON.parse(data)[0].rate)
      .catch(() => this.exchange = 'error');
    for (const image of this.product?.images) {
      this.images.push(new ImageItem({ src: image.file, thumb: image.file }));
    }
  }

  // tslint:disable-next-line:typedef
  addToCart(product: Product) {
    if (this.available !== 0) {
      this.cartService.addToCart(product);
    }
  }
}
