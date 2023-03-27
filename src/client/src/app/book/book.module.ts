import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatExpansionModule} from '@angular/material/expansion';
import { NgParticlesModule } from 'ng-particles';
import { BookRoutingModule } from './book-routing.module';
import { SharedModule } from '../shared/shared.module';
import { BookListComponent } from './book-list/book-list.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader';
import {GalleryModule} from 'ng-gallery';
import {MatTooltipModule} from '@angular/material/tooltip';
import {FormsModule} from '@angular/forms';
import {AddToLibraryDialogComponent} from './book-details/add-to-library-dialog/add-to-library-dialog.component';
import {ReportDialogComponent} from './book-details/report-dialog/report-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';

@NgModule({
  declarations: [BookListComponent, BookDetailsComponent, AddToLibraryDialogComponent, ReportDialogComponent],
  imports: [
    CommonModule,
    BookRoutingModule,
    SharedModule,
    MatExpansionModule,
    NgParticlesModule,
    NgxSkeletonLoaderModule,
    GalleryModule,
    MatTooltipModule,
    FormsModule,
    MatDialogModule,
    MatInputModule
  ]
})
export class BookModule { }
