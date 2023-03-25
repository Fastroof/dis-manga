import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ProductCreate} from '../../../shared/interfaces/product-create';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-product-edit-dialog',
  templateUrl: './product-edit-dialog.component.html',
  styleUrls: ['./product-edit-dialog.component.scss']
})
export class ProductEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductCreate,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    this.apiService.editProduct(this.data.id, this.data.category_id, this.data.name, this.data.info, this.data.price)
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
