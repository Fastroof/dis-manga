import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';
import {ProductIncoming} from '../../../shared/interfaces/product-incoming';

@Component({
  selector: 'app-product-incoming-edit-dialog',
  templateUrl: './product-incoming-edit-dialog.component.html',
  styleUrls: ['./product-incoming-edit-dialog.component.scss']
})
export class ProductIncomingEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductIncomingEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductIncoming,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    this.apiService.updateProductIncoming(this.data.id, this.data.quantity)
      .subscribe( {
        next: value => this.result = value,
        error: err => this.result = err.error
      });
    this.isEdited = true;
  }
}
