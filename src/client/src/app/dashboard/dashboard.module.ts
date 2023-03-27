import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatMenuModule } from '@angular/material/menu';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardIndexComponent } from './dashboard-index/dashboard-index.component';
import { SharedModule } from '../shared/shared.module';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { DashboardModeratorComponent } from './dashboard-moderator/dashboard-moderator.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import { HelpRequestProcessDialogComponent } from './dashboard-moderator/help-request-process-dialog/help-request-process-dialog.component';
import {MatSelectModule} from '@angular/material/select';
import {DashboardModeratorReportsComponent} from './dashboard-moderator-reports/dashboard-moderator-reports.component';
import {
  ReportProcessDialogComponent
} from './dashboard-moderator-reports/help-request-process-dialog/report-process-dialog.component';
import {DashboardBooksComponent} from './dashboard-books/dashboard-books.component';
import {BookDialogComponent} from './dashboard-books/book-dialog/book-dialog.component';
import {BookEditDialogComponent} from './dashboard-books/book-edit-dialog/book-edit-dialog.component';
import {BookDeleteDialogComponent} from './dashboard-books/book-delete-dialog/book-delete-dialog.component';

@NgModule({
  declarations: [
    DashboardLayoutComponent,
    DashboardIndexComponent,
    DashboardProfileComponent,
    DashboardModeratorComponent,
    DashboardModeratorReportsComponent,
    ReportProcessDialogComponent,
    DashboardBooksComponent,
    BookDialogComponent,
    BookEditDialogComponent,
    BookDeleteDialogComponent,
    HelpRequestProcessDialogComponent
  ],
  imports: [
    CommonModule, DashboardRoutingModule, SharedModule, MatMenuModule, MatExpansionModule, MatTabsModule,
    MatFormFieldModule, MatInputModule, MatTableModule, MatDialogModule, FormsModule, MatSelectModule]
})
export class DashboardModule {}
