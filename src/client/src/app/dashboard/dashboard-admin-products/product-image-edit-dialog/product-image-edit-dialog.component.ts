import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';
import {ProductImage} from '../../../shared/interfaces/product-image';

@Component({
  selector: 'app-product-image-edit-dialog',
  templateUrl: './product-image-edit-dialog.component.html',
  styleUrls: ['./product-image-edit-dialog.component.scss']
})
export class ProductImageEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductImageEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductImage,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    this.apiService.editProductImage(this.data.id, this.data.product_id)
      .subscribe({
        next: value => {
          this.result = value;
        },
        error: err => {
          this.result = err.error;
        }
      });
    this.isEdited = true;
  }
}
