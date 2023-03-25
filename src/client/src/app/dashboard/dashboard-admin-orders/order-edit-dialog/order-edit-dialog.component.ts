import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-order-edit-dialog',
  templateUrl: './order-edit-dialog.component.html',
  styleUrls: ['./order-edit-dialog.component.scss']
})
export class OrderEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<OrderEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {id: number, status: string},
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.data.status != null) {
      this.apiService.updateOrderStatus(this.data.id, this.data.status)
        .subscribe({
          next: value => {
            this.result = value;
            this.data.status = this.result;
          },
          error: err => {
            this.result = err.error;
            this.data.status = this.result;
          }
        });
    } else {
      this.result = 'Не заданий статус замовлення';
    }
    this.isEdited = true;
  }

}
