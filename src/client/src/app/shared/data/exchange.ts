import {Injectable} from '@angular/core';
import {Resolve} from '@angular/router';
import {ExchangeApiService} from '../../_services/exchange-api.service';
import {Exchange} from '../interfaces/exchange';

@Injectable({
  providedIn: 'root'
})
export class ExchangeResolver implements Resolve<Exchange[]> {
  constructor(private api: ExchangeApiService) {}

  resolve(): Exchange[] {
    return this.getExchange();
  }

  // tslint:disable-next-line:typedef
  getExchange() {
    const ex: Exchange[] = [];
    this.api.getExchange()
      .subscribe(resp => {
        if (resp.body !== null) {
          for (const data of resp.body) {
            ex.push(data);
          }
        }
      });
    return ex;
  }

}
