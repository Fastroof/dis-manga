import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../../_services/token-storage.service';
import {ActivatedRoute} from '@angular/router';
import {Book} from '../../shared/interfaces/book';
import {ApiService} from '../../_services/api.service';

@Component({
  selector: 'dashboard-index',
  templateUrl: './dashboard-index.component.html',
  styleUrls: ['./dashboard-index.component.scss']
})
export class DashboardIndexComponent implements OnInit {
  isLoggedIn: any;
  books: Book[] = [];

  constructor(private activatedRoute: ActivatedRoute,
              private tokenStorageService: TokenStorageService,
              private apiService: ApiService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.books = d['personalLibrary'];
    });
  }

  delete(id: number): void {
    this.apiService.deleteBookFromPersonalLibrary(id).subscribe({
      next: value => {
        console.log(value);
        window.location.reload();
      },
      error: err => {
        console.error(err.error);
      }
    });
  }
}
