import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-product-image-delete-dialog',
  templateUrl: './product-image-delete-dialog.component.html',
  styleUrls: ['./product-image-delete-dialog.component.scss']
})
export class ProductImageDeleteDialogComponent implements OnInit {

  isDeleted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductImageDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.data != null) {
      this.apiService.deleteProductImage(this.data)
        .subscribe({
          next: value => {
            this.result = value;
          },
          error: err => {
            this.result = err.error;
          }
        });
    } else {
      this.result = 'Не задана id зображення';
    }
    this.isDeleted = true;
  }
}
