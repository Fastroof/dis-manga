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

@NgModule({
  declarations: [
    DashboardLayoutComponent,
    DashboardIndexComponent,
    DashboardProfileComponent,
    DashboardModeratorComponent,
    HelpRequestProcessDialogComponent
  ],
  imports: [
    CommonModule, DashboardRoutingModule, SharedModule, MatMenuModule, MatExpansionModule, MatTabsModule,
    MatFormFieldModule, MatInputModule, MatTableModule, MatDialogModule, FormsModule, MatSelectModule]
})
export class DashboardModule {}
