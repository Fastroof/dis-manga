import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.scss']
})
export class ReportDialogComponent implements OnInit {

  isSubmitted = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ReportDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {id: number, text: string},
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.data.id != null && this.data.text != null) {
      this.apiService.sendReport(this.data.id, this.data.text)
        .subscribe({
          next: value => {
            this.result = JSON.parse(value.toString()).text;
          },
          error: err => {
            this.result = JSON.parse(err.error.toString()).text;
          }
        });
    } else {
      this.result = 'Не введено текст скарги';
    }
    this.isSubmitted = true;
  }
}
