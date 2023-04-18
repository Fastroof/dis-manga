import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {BookDialogComponent} from './book-dialog/book-dialog.component';
import {BookEditDialogComponent} from './book-edit-dialog/book-edit-dialog.component';
import {BookDeleteDialogComponent} from './book-delete-dialog/book-delete-dialog.component';
import {environment} from '../../../environments/environment';
import {Book} from '../../shared/interfaces/book';
import {TokenStorageService} from '../../_services/token-storage.service';

@Component({
  selector: 'dashboard-books',
  templateUrl: './dashboard-books.component.html',
  styleUrls: ['./dashboard-books.component.scss']
})
export class DashboardBooksComponent implements OnInit {

  core = environment.core;
  books: MatTableDataSource<Book> = new MatTableDataSource<Book>();

  constructor(public dialog: MatDialog, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    fetch(environment.core + '/books?owner_id=' + this.tokenStorageService.getUser().id)
      .then((response) => response.json())
      .then((data: Book[]) => this.books = new MatTableDataSource<Book>(data.sort((a, b) => {
        return a.id - b.id;
      })));
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.books.filter = filterValue.trim().toLowerCase();
  }

  addBook(): void {
    const dialogRef = this.dialog.open(BookDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  editBook(id: number, name: string, tagId: number): void {
    const dialogRef = this.dialog.open(BookEditDialogComponent, {
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '100%',
      width: '100%',
      data: {id, name, tagId}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  deleteBook(id: number): void {
    const dialogRef = this.dialog.open(BookDeleteDialogComponent, {
      width: '300px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }
}
