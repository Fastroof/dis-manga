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

@NgModule({
  declarations: [BookListComponent, BookDetailsComponent],
    imports: [
        CommonModule,
        BookRoutingModule,
        SharedModule,
        MatExpansionModule,
        NgParticlesModule,
        NgxSkeletonLoaderModule,
        GalleryModule,
        MatTooltipModule,
        FormsModule
    ]
})
export class BookModule { }
