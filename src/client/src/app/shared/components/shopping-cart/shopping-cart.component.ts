import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {OrderItem} from '../../interfaces/order-item';
import {CartService} from '../../../_services/cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {
  cart: OrderItem[] = [];
  sum: string | undefined;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private cartService: CartService) {}

  ngOnInit(): void {
    this.cart = this.cartService.getItems();
    this.refreshSum();
  }

  subQuantity(indexInCart: number): void {
    this.cartService.subQuantity(indexInCart);
    this.refreshSum();
  }

  addQuantity(indexInCart: number): void {
    this.cartService.addQuantity(indexInCart);
    this.refreshSum();
  }

  changeQuantity(indexInCart: number, event: any): void {
    this.cartService.changeQuantity(indexInCart, event);
    this.refreshSum();
  }

  removeOrderItem(indexInCart: number): void {
    this.cartService.removeOrderItem(indexInCart);
    this.refreshSum();
  }

  private refreshSum(): void {
    let temp = 0;
    for (const cartItem of this.cart) {
      temp += cartItem.quantity * cartItem.product.price;
    }
    this.sum = temp.toFixed(2);
  }

  toProduct(id: number): void {
    window.location.href = '/products/' + id;
  }
}
