import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-product-image-dialog',
  templateUrl: './product-image-dialog.component.html',
  styleUrls: ['./product-image-dialog.component.scss']
})
export class ProductImageDialogComponent implements OnInit {

  isCreated = false;
  result = 'Завантаження...';
  fileName: any;
  file: File | undefined;

  constructor(
    public dialogRef: MatDialogRef<ProductImageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.data == null) {
      this.result = 'Не задано id товара';
    } else if (this.file == null) {
      this.result = 'Не прикріплено файл';
    } else {
      this.apiService.uploadProductImage(this.data, this.file)
        .subscribe({
          next: value => {
            this.result = JSON.stringify(value, null, 4);
          },
          error: err => {
            this.result = err.error;
          }
        });
    }
    this.isCreated = true;
  }

  onFileSelected(event: Event): void {
    // @ts-ignore
    const file: File = event.target.files[0];
    if (file) {
      this.fileName = file.name;
      this.file = file;
    }
  }
}
