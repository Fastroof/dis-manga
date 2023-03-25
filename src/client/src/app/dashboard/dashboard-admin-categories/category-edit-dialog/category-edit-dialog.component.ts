import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-category-edit-dialog',
  templateUrl: './category-edit-dialog.component.html',
  styleUrls: ['./category-edit-dialog.component.scss']
})
export class CategoryEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<CategoryEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {id: number, name: string},
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.data.name != null) {
      this.apiService.editCategory(this.data.id, this.data.name)
      .subscribe({
          next: value => {
            this.result = value;
            this.data.name = this.result;
          },
          error: err => {
            this.result = err.error;
            this.data.name = this.result;
          }
        });
    } else {
      this.result = 'Не задана назва категорії';
    }
    this.isEdited = true;
  }
}
