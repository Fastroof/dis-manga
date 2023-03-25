import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductIncomingDeleteDialogComponent } from './product-incoming-delete-dialog.component';

describe('ProductIncomingDeleteDialogComponent', () => {
  let component: ProductIncomingDeleteDialogComponent;
  let fixture: ComponentFixture<ProductIncomingDeleteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductIncomingDeleteDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductIncomingDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
