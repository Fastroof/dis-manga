import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductIncomingEditDialogComponent } from './product-incoming-edit-dialog.component';

describe('ProductIncomingEditDialogComponent', () => {
  let component: ProductIncomingEditDialogComponent;
  let fixture: ComponentFixture<ProductIncomingEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductIncomingEditDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductIncomingEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
