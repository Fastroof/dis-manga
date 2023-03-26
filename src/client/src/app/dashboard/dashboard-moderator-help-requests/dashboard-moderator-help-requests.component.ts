import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {HelpRequestProcessDialogComponent} from './help-request-process-dialog/help-request-process-dialog.component';
import {environment} from '../../../environments/environment';
import {HelpRequest} from '../../shared/interfaces/help-request';

@Component({
  selector: 'app-dashboard-moderator-help-requests',
  templateUrl: './dashboard-moderator-help-requests.component.html',
  styleUrls: ['./dashboard-moderator-help-requests.component.scss']
})
export class DashboardModeratorHelpRequestsComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  categories: MatTableDataSource<HelpRequest> = new MatTableDataSource<HelpRequest>();

  @ViewChild(MatTable) table: MatTable<HelpRequest> | undefined;

  ngOnInit(): void {
    fetch(environment.core + '/moderator/help-requests')
      .then((response) => response.json())
      .then((data: HelpRequest[]) => this.categories = new MatTableDataSource<HelpRequest>(data.sort((a, b) => {
        return a.id - b.id;
      })));
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.categories.filter = filterValue.trim().toLowerCase();
  }

  processHelpRequest(id: number): void {
    const dialogRef = this.dialog.open(HelpRequestProcessDialogComponent, {
      width: '280px',
      data: {id}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }
}
