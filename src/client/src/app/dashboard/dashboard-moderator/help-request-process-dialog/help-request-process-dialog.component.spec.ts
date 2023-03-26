import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelpRequestProcessDialogComponent } from './help-request-process-dialog.component';

describe(' HelpRequestProcessDialogComponent', () => {
  let component: HelpRequestProcessDialogComponent;
  let fixture: ComponentFixture<HelpRequestProcessDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HelpRequestProcessDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelpRequestProcessDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
