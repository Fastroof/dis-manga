import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-category-dialog',
  templateUrl: './category-dialog.component.html',
  styleUrls: ['./category-dialog.component.scss']
})
export class CategoryDialogComponent implements OnInit {

  isCreated = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<CategoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.data != null) {
      this.apiService.addCategory(this.data)
        .subscribe({
          next: value => {
            this.result = value;
            this.data = this.result;
          },
          error: err => {
            this.result = err.error;
            this.data = this.result;
          }
        });
    } else {
      this.result = 'Не задана назва категорії';
    }
    this.isCreated = true;
  }
}
