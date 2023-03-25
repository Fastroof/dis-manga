import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdminProductsComponent } from './dashboard-admin-products.component';

describe('DashboardAdminProductsComponent', () => {
  let component: DashboardAdminProductsComponent;
  let fixture: ComponentFixture<DashboardAdminProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardAdminProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardAdminProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
