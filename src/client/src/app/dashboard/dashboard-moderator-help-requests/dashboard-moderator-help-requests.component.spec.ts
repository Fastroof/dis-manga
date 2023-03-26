import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardModeratorHelpRequestsComponent } from './dashboard-moderator-help-requests.component';

describe('DashboardAdminCategoriesComponent', () => {
  let component: DashboardModeratorHelpRequestsComponent;
  let fixture: ComponentFixture<DashboardModeratorHelpRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardModeratorHelpRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardModeratorHelpRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
