import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-category-delete-dialog',
  templateUrl: './category-delete-dialog.component.html',
  styleUrls: ['./category-delete-dialog.component.scss']
})
export class CategoryDeleteDialogComponent implements OnInit {

  isDeleted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<CategoryDeleteDialogComponent>,
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
      this.apiService.deleteCategory(this.data)
        .subscribe({
          next: value => {
            this.result = value;
          },
          error: err => {
            this.result = err.error;
          }
        });
    } else {
      this.result = 'Не задана id категорії';
    }
    this.isDeleted = true;
  }
}
