import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'book-edit-dialog',
  templateUrl: './book-edit-dialog.component.html',
  styleUrls: ['./book-edit-dialog.component.scss']
})
export class BookEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<BookEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {name: string, tagId: number},
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // onSubmit() {
  //   this.apiService.editProduct(this.data.id, this.data.category_id, this.data.name, this.data.info, this.data.price)
  //     .subscribe({
  //       next: value => {
  //         this.result = value;
  //       },
  //       error: err => {
  //         this.result = err.error;
  //       }
  //     });
  //   this.isEdited = true;
  // }
}
