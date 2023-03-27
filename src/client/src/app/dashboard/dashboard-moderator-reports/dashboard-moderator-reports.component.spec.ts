import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardModeratorReportsComponent } from './dashboard-moderator-reports.component';

describe('DashboardAdminCategoriesComponent', () => {
  let component: DashboardModeratorReportsComponent;
  let fixture: ComponentFixture<DashboardModeratorReportsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardModeratorReportsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardModeratorReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
