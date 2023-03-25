import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdminOrdersComponent } from './dashboard-admin-orders.component';

describe('DashboardAdminOrdersComponent', () => {
  let component: DashboardAdminOrdersComponent;
  let fixture: ComponentFixture<DashboardAdminOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardAdminOrdersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardAdminOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
