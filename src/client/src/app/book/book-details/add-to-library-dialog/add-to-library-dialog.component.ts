import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'add-to-library-dialog',
  templateUrl: './add-to-library-dialog.component.html',
  styleUrls: ['./add-to-library-dialog.component.scss']
})
export class AddToLibraryDialogComponent implements OnInit {

  isSubmitted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<AddToLibraryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public id: number,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.id != null) {
      this.apiService.addToPersonalLibrary(this.id)
        .subscribe({
          next: value => {
            this.result = JSON.parse(value.toString()).text;
          },
          error: err => {
            this.result = JSON.parse(err.error.toString()).text;
          }
        });
    } else {
      this.result = 'Не задана id книги';
    }
    this.isSubmitted = true;
  }
}
