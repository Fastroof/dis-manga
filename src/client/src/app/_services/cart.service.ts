import { Injectable } from '@angular/core';
import {OrderItem} from '../shared/interfaces/order-item';
import {Product} from '../shared/interfaces/product';
import {ShoppingCartComponent} from '../shared/components/shopping-cart/shopping-cart.component';
import {MatDialog} from '@angular/material/dialog';
import {OrderItemForOrder} from '../shared/interfaces/order-item-for-order';

const CART = 'cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  orderItems: OrderItem[] = [];

  constructor(public dialog: MatDialog) { }

  public getOrderItems(): OrderItemForOrder[] {
    const temp: OrderItemForOrder[] = [];
    for (const orderItem of this.getItems()) {
      temp.push({
        product_id: orderItem.product.id,
        initial_price: 0,
        ex_quantity: orderItem.quantity,
        price: orderItem.product.price
      });
    }
    return temp;
  }

  public saveCart(): void {
    window.localStorage.removeItem(CART);
    window.localStorage.setItem(CART, JSON.stringify(this.orderItems));
  }

  public removeCart(): void {
    window.localStorage.removeItem(CART);
    this.orderItems = [];
  }

  public getCart(): any {
    // @ts-ignore
    return JSON.parse(localStorage.getItem(CART));
  }

  // tslint:disable-next-line:typedef
  openCart() {
    const temp = this.getCart();
    if (temp != null) {
      this.orderItems = temp;
    }
    const dialogRef = this.dialog.open(ShoppingCartComponent, {
      maxWidth: '50vw',
      width: '100%'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        window.location.href = '/order-confirm';
      }
    });
  }

  // tslint:disable-next-line:typedef
  addToCart(product: Product) {
    const index = this.orderItems.findIndex(value => value.product.id === product.id);
    if (index !== -1) {
      this.orderItems[index].quantity++;
    } else {
      this.orderItems.push({
        product,
        quantity: 1
      });
    }
    this.saveCart();
    this.openCart();
  }

  // tslint:disable-next-line:typedef
  getItems() {
    const temp = this.getCart();
    if (temp != null) {
      this.orderItems = temp;
    }
    return this.orderItems;
  }

  // tslint:disable-next-line:typedef
  removeOrderItem(indexInCart: number) {
    this.orderItems.splice(indexInCart, 1);
    this.saveCart();
  }

  // tslint:disable-next-line:typedef
  subQuantity(indexInCart: number) {
    if (this.orderItems[indexInCart].quantity > 1) {
      this.orderItems[indexInCart].quantity--;
    }
    this.saveCart();
  }

  // tslint:disable-next-line:typedef
  addQuantity(indexInCart: number) {
    this.orderItems[indexInCart].quantity++;
    this.saveCart();
  }

  // tslint:disable-next-line:typedef
  changeQuantity(indexInCart: number, event: any) {
    const input = (event.target as HTMLInputElement);
    // tslint:disable-next-line:radix
    const temp = parseInt(input.value);
    if (isNaN(temp) || temp <= 0) {
      this.orderItems[indexInCart].quantity = 1;
      input.value = String(1);
    } else {
      this.orderItems[indexInCart].quantity = temp;
      input.value = String(temp);
    }
    this.saveCart();
  }

}
