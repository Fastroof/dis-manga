import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'book-delete-dialog',
  templateUrl: './book-delete-dialog.component.html',
  styleUrls: ['./book-delete-dialog.component.scss']
})
export class BookDeleteDialogComponent implements OnInit {

  isDeleted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<BookDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public id: number,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.id != null) {
      this.apiService.deleteBook(this.id)
        .subscribe({
          next: value => {
            this.result = value;
          },
          error: err => {
            this.result = err.error;
          }
        });
    } else {
      this.result = 'Не задана id книги';
    }
    this.isDeleted = true;
  }
}
