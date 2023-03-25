import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdminCategoriesComponent } from './dashboard-admin-categories.component';

describe('DashboardAdminCategoriesComponent', () => {
  let component: DashboardAdminCategoriesComponent;
  let fixture: ComponentFixture<DashboardAdminCategoriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardAdminCategoriesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardAdminCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
