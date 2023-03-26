import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ApiService} from '../../../_services/api.service';

@Component({
  selector: 'app-help-request-process-dialog',
  templateUrl: './help-request-process-dialog.component.html',
  styleUrls: ['./help-request-process-dialog.component.scss']
})
export class HelpRequestProcessDialogComponent implements OnInit {

  isProcessed = false;
  result = 'Завантаження...';

  constructor(
    public dialogRef: MatDialogRef<HelpRequestProcessDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.data != null) {
      this.apiService.processHelpRequest(this.data)
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
