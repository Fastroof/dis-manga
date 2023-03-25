import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductImageEditDialogComponent } from './product-image-edit-dialog.component';

describe('ProductImageEditDialogComponent', () => {
  let component: ProductImageEditDialogComponent;
  let fixture: ComponentFixture<ProductImageEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductImageEditDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductImageEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
