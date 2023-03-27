import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardBooksComponent } from './dashboard-books.component';

describe('DashboardAdminProductsComponent', () => {
  let component: DashboardBooksComponent;
  let fixture: ComponentFixture<DashboardBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardBooksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
