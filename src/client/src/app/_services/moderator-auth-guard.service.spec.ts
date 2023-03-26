import { TestBed } from '@angular/core/testing';

import { ModeratorAuthGuardService } from './moderator-auth-guard.service';

describe('ModeratorAuthGuardService', () => {
  let service: ModeratorAuthGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModeratorAuthGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
