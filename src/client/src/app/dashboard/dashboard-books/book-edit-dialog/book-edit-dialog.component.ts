import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'book-edit-dialog',
  templateUrl: './book-edit-dialog.component.html',
  styleUrls: ['./book-edit-dialog.component.scss']
})
export class BookEditDialogComponent implements OnInit {

  isEdited = false;
  result = 'Завантаження...';
  files: File[] | undefined;
  cover: File | undefined;

  constructor(
    public dialogRef: MatDialogRef<BookEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {id: number, name: string, tagId: number},
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    this.apiService.editBook(this.data.id, this.data.name, this.data.tagId, this.files, this.cover)
      .subscribe({
        next: value => {
          this.result = value.toString();
        },
        error: err => {
          this.result = err.error.toString();
        }
      });
    this.isEdited = true;
  }

  onFilesChange(event: Event): void {
    this.files = [];
    // @ts-ignore
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < event.target.files.length; i++) {
      // @ts-ignore
      const file = event.target.files[i];
      if (file) {
        this.files?.push(file);
      }
    }
  }

  onCoverChange(event: Event): void {
    // @ts-ignore
    const file: File = event.target.files[0];
    if (file) {
      this.cover = file;
    }
  }
}
