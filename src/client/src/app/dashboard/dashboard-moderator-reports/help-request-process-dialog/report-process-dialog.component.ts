import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'report-process-dialog',
  templateUrl: './report-process-dialog.component.html',
  styleUrls: ['./report-process-dialog.component.scss']
})
export class ReportProcessDialogComponent implements OnInit {

  isProcessed = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<ReportProcessDialogComponent>,
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
      this.apiService.processReport(this.data)
        .subscribe({
          next: value => {
            this.result = value.toString();
          },
          error: err => {
            this.result = err.error;
          }
        });
    } else {
      this.result = 'Не задана id категорії';
    }
    this.isProcessed = true;
  }
}
