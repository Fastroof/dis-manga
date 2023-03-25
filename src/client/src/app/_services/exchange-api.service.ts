import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Exchange} from '../shared/interfaces/exchange';

@Injectable({
  providedIn: 'root'
})
export class ExchangeApiService {

  constructor(private http: HttpClient) { }

  getExchange(): Observable<HttpResponse<Exchange[]>> {
    return this.http.get<Exchange[]>('https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5', {observe: 'response'});
  }
}
