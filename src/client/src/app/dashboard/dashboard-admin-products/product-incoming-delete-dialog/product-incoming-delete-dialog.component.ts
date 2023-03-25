import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-product-incoming-delete-dialog',
  templateUrl: './product-incoming-delete-dialog.component.html',
  styleUrls: ['./product-incoming-delete-dialog.component.scss']
})
export class ProductIncomingDeleteDialogComponent implements OnInit {

  isDeleted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ProductIncomingDeleteDialogComponent>,
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
      this.apiService.deleteProductIncoming(this.data)
        .subscribe({
          next: value => {
            this.result = value;
          },
          error: err => {
            this.result = err.error;
          }
        });
    } else {
      this.result = 'Не задане id надходження';
    }
    this.isDeleted = true;
  }
}
