import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductIncomingDialogComponent } from './product-incoming-dialog.component';

describe('ProductIncomingDialogComponent', () => {
  let component: ProductIncomingDialogComponent;
  let fixture: ComponentFixture<ProductIncomingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductIncomingDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductIncomingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
