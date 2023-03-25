import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-product-delete-dialog',
  templateUrl: './product-delete-dialog.component.html',
  styleUrls: ['./product-delete-dialog.component.scss']
})
export class ProductDeleteDialogComponent implements OnInit {

  isDeleted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductDeleteDialogComponent>,
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
      this.apiService.deleteProduct(this.data)
        .subscribe({
          next: value => {
            this.result = value;
          },
          error: err => {
            this.result = err.error;
          }
        });
    } else {
      this.result = 'Не задана id продукта';
    }
    this.isDeleted = true;
  }
}
