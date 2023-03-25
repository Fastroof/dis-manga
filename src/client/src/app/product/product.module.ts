import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatExpansionModule} from '@angular/material/expansion';
import { NgParticlesModule } from 'ng-particles';
import { ProductRoutingModule } from './product-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader';
import {GalleryModule} from 'ng-gallery';
import {MatTooltipModule} from '@angular/material/tooltip';

@NgModule({
  declarations: [ProductListComponent, ProductDetailsComponent],
    imports: [
        CommonModule,
        ProductRoutingModule,
        SharedModule,
        MatExpansionModule,
        NgParticlesModule,
        NgxSkeletonLoaderModule,
        GalleryModule,
        MatTooltipModule
    ]
})
export class ProductModule { }
