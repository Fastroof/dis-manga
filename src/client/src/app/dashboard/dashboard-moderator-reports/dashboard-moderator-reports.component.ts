import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {environment} from '../../../environments/environment';
import {Report} from '../../shared/interfaces/report';
import {ReportProcessDialogComponent} from './help-request-process-dialog/report-process-dialog.component';

@Component({
  selector: 'dashboard-moderator-reports',
  templateUrl: './dashboard-moderator-reports.component.html',
  styleUrls: ['./dashboard-moderator-reports.component.scss']
})
export class DashboardModeratorReportsComponent implements OnInit {

  constructor(public dialog: MatDialog) {}

  reports: MatTableDataSource<Report> = new MatTableDataSource<Report>();

  @ViewChild(MatTable) table: MatTable<Report> | undefined;

  ngOnInit(): void {
    fetch(environment.core + '/moderator/reports')
      .then((response) => response.json())
      .then((data: Report[]) => this.reports = new MatTableDataSource<Report>(data.sort((a, b) => {
        return a.id - b.id;
      })));
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.reports.filter = filterValue.trim().toLowerCase();
  }

  processReport(id: number): void {
    const dialogRef = this.dialog.open(ReportProcessDialogComponent, {
      width: '280px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }
}
