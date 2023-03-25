import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NovaposhtaService {

  constructor(private http: HttpClient) { }

  findCity(city: string): Observable<any> {
    return this.http.post<any>(environment.novaposhta, {
      apiKey: '',
      modelName: 'Address',
      calledMethod: 'searchSettlements',
      methodProperties: {
        CityName: city,
        Limit: '20',
        Page: '1'
      }
    });
  }

  // findCity(city: string) {
  //   return  fetch('https://api.novaposhta.ua/v2.0/json/', {
  //     method: 'POST', // *GET, POST, PUT, DELETE, etc.
  //     mode: 'cors', // no-cors, *cors, same-origin
  //     cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
  //     credentials: 'same-origin', // include, *same-origin, omit
  //     headers: {
  //       'Content-Type': 'application/json'
  //       // 'Content-Type': 'application/x-www-form-urlencoded',
  //     },
  //     redirect: 'follow', // manual, *follow, error
  // tslint:disable-next-line:max-line-length
  //     referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
  //     body: '{\n' +
  //       '\t\'apiKey\': \'\',\n' +
  //       '\t\'modelName\': \'Address\',\n' +
  //       '\t\'calledMethod\': \'searchSettlements\',\n' +
  //       '\t\'methodProperties\': {\n' +
  //       '\t\t\'CityName\': \'+ city +'\',\n' +
  //       '\t\t\'Limit\': \'20\',\n' +
  //       '\t\t\'Page\': \'1\'\n' +
  //       '\t}\n' +
  //       '}' // body data type must match 'Content-Type' header
  //   })
  //     .then((response) => response.json().)
  // }
}
