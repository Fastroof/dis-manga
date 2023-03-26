import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardModeratorComponent } from './dashboard-moderator.component';

describe('DashboardAdminCategoriesComponent', () => {
  let component: DashboardModeratorComponent;
  let fixture: ComponentFixture<DashboardModeratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardModeratorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardModeratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
