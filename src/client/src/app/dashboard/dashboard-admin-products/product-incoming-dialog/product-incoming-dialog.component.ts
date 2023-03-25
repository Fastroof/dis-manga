import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';
import {ProductIncoming} from '../../../shared/interfaces/product-incoming';

@Component({
  selector: 'app-product-incoming-dialog',
  templateUrl: './product-incoming-dialog.component.html',
  styleUrls: ['./product-incoming-dialog.component.scss']
})
export class ProductIncomingDialogComponent implements OnInit {

  isCreated = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductIncomingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductIncoming,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    this.apiService.addProductIncoming(this.data.product_id, this.data.supplier, this.data.initial_price, this.data.quantity)
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
