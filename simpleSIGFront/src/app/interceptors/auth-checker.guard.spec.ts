import { TestBed } from '@angular/core/testing';

import { LoggedCheckGuard } from './auth-checker.guard';

describe('AuthCheckerService', () => {
  let service: LoggedCheckGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoggedCheckGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
