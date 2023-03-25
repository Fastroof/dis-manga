import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductImageDeleteDialogComponent } from './product-image-delete-dialog.component';

describe('ProductImageDeleteDialogComponent', () => {
  let component: ProductImageDeleteDialogComponent;
  let fixture: ComponentFixture<ProductImageDeleteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductImageDeleteDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductImageDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
