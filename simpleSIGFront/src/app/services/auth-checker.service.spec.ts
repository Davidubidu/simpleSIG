import { TestBed } from '@angular/core/testing';

import { AuthCheckerService } from './auth-checker.service';

describe('AuthCheckerService', () => {
  let service: AuthCheckerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthCheckerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
