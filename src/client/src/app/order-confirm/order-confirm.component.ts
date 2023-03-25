import { Component, OnInit } from '@angular/core';
import {NovaposhtaService} from '../_services/novaposhta.service';
import {TokenStorageService} from '../_services/token-storage.service';
import {CartService} from '../_services/cart.service';
import {OrderItem} from '../shared/interfaces/order-item';
import {ApiService} from '../_services/api.service';

@Component({
  selector: 'app-order-confirm',
  templateUrl: './order-confirm.component.html',
  styleUrls: ['./order-confirm.component.scss']
})
export class OrderConfirmComponent implements OnInit {

  orderCreated = false;
  result = 'Трапилася помилка';

  isLoggedIn = false;

  form: any = {};
  cities: string[] = [];
  cart: OrderItem[] = [];
  sum: string | undefined;
  paymentType = 'Після отримання товару';

  constructor(private tokenStorageService: TokenStorageService,
              private novaposhtaService: NovaposhtaService,
              private cartService: CartService,
              private apiService: ApiService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.form.username = user.username;
      this.form.email = user.email;
      this.form.user_id = user.id;
    }
    this.cart = this.cartService.getItems();
    this.refreshSum();
  }

  onSubmit(): void {
    const payment = 'Не оплачено';
    this.apiService.createOrder(
      this.form.user_id,
      this.form.city,
      this.form.email,
      payment,
      this.paymentType,
      this.form.phone,
      this.form.username,
      this.cartService.getOrderItems())
      .subscribe({
        next: value => {
          this.cartService.removeCart();
          this.orderCreated = true;
          this.result = value;
        }, error: err => {
          this.orderCreated = true;
          this.result = err.error;
        }
      });
  }

  findCity(): void {
    const temp: string[] = [];
    this.novaposhtaService.findCity(this.form.city).subscribe(value => {
      if (value.data.length !== 0) {
        value.data[0].Addresses.forEach((address: { Present: string; }) => temp.push(address.Present));
      }
    });
    this.cities = temp;
  }

  changeFormCity(option: string): void {
    this.form.city = option;
    this.cities = [];
  }

  private refreshSum(): void {
    let temp = 0;
    for (const cartItem of this.cart) {
      temp += cartItem.quantity * cartItem.product.price;
    }
    this.sum = temp.toFixed(2);
  }
}
