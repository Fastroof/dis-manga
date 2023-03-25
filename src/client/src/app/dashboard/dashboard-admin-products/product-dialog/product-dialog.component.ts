import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';
import {ProductCreate} from '../../../shared/interfaces/product-create';

@Component({
  selector: 'app-product-dialog',
  templateUrl: './product-dialog.component.html',
  styleUrls: ['./product-dialog.component.scss']
})
export class ProductDialogComponent implements OnInit {

  isCreated = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductCreate,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    this.apiService.addProduct(this.data.category_id, this.data.name, this.data.info, this.data.price)
      .subscribe({
        next: value => {
          this.result = value;
        },
        error: err => {
          this.result = err.error;
        }
      });
    this.isCreated = true;
  }
}
