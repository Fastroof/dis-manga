import { Component, OnInit } from '@angular/core';
import {Order} from '../../shared/interfaces/order';
import {environment} from '../../../environments/environment';
import {TokenStorageService} from '../../_services/token-storage.service';
import {OrderItemForOrder} from '../../shared/interfaces/order-item-for-order';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'll-dashboard-index',
  templateUrl: './dashboard-index.component.html',
  styleUrls: ['./dashboard-index.component.scss']
})
export class DashboardIndexComponent implements OnInit {
  orders: Order[] = [];
  isLoggedIn = false;
  userId = '-1';
  email = 'error';

  constructor(private activatedRoute: ActivatedRoute,
              private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.userId = user.id;
      this.email = user.email;
    }
    fetch(environment.order + '/order?email=' + this.email + '&user_id=' + this.userId)
      .then((response) => response.json())
      // tslint:disable-next-line:only-arrow-functions typedef
      .then((data: Order[]) => this.orders = data.sort(function(a, b) {
        return a.id - b.id;
      }));
  }

  sumPrices(arr: OrderItemForOrder[]): number {
    return arr.reduce((accumulator, object) => {
      return accumulator + object.ex_quantity * object.price;
    }, 0);
  }
}
