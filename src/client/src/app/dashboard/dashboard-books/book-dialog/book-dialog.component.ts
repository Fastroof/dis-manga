import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'book-dialog',
  templateUrl: './book-dialog.component.html',
  styleUrls: ['./book-dialog.component.scss']
})
export class BookDialogComponent implements OnInit {

  isCreated = false;
  result = 'Завантаження...';
  name: string | undefined;
  tagId: number | undefined;
  fileNames: string[] = [];
  coverName: string | undefined;
  files: File[] | undefined;
  cover: File | undefined;

  constructor(
    public dialogRef: MatDialogRef<BookDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {},
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    console.log(this.name, this.tagId, this.files, this.cover);
    // this.apiService.addBook(this.name, this.tagId, this.files, this.cover)
    //   .subscribe({
    //     next: value => {
    //       this.result = value;
    //     },
    //     error: err => {
    //       this.result = err.error;
    //     }
    //   });
    // this.isCreated = true;
  }

  onFilesChange(event: Event): void {
    this.fileNames = [];
    this.files = [];
    // @ts-ignore
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < event.target.files.length; i++) {
      // @ts-ignore
      const file = event.target.files[i];
      if (file) {
        this.fileNames.push(file.name);
        this.files?.push(file);
      }
    }
  }

  onCoverChange(event: Event): void {
    // @ts-ignore
    const file: File = event.target.files[0];
    if (file) {
      this.coverName = file.name;
      this.cover = file;
    }
  }
}
