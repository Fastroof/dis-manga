import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {HelpRequestProcessDialogComponent} from './help-request-process-dialog/help-request-process-dialog.component';
import {environment} from '../../../environments/environment';
import {HelpRequest} from '../../shared/interfaces/help-request';
import {TokenStorageService} from '../../_services/token-storage.service';

@Component({
  selector: 'app-dashboard-moderator',
  templateUrl: './dashboard-moderator.component.html',
  styleUrls: ['./dashboard-moderator.component.scss']
})
export class DashboardModeratorComponent implements OnInit {

  constructor(public dialog: MatDialog, private tokenStorageService: TokenStorageService) { }

  requests: MatTableDataSource<HelpRequest> = new MatTableDataSource<HelpRequest>();

  @ViewChild(MatTable) table: MatTable<HelpRequest> | undefined;

  ngOnInit(): void {
    fetch(environment.core + '/moderator/help-requests', {headers: new Headers({
        Authorization: `Bearer ${this.tokenStorageService.getToken()}`
      })})
      .then((response) => response.json())
      .then((data: HelpRequest[]) => this.requests = new MatTableDataSource<HelpRequest>(data.sort((a, b) => {
        return a.id - b.id;
      })))
      .catch((error) => {console.error('Error:', error); });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.requests.filter = filterValue.trim().toLowerCase();
  }

  processHelpRequest(id: number): void {
    const dialogRef = this.dialog.open(HelpRequestProcessDialogComponent, {
      width: '280px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }
}
