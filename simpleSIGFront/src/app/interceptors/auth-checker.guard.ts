import { Injectable } from '@angular/core';
import {
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { map, take } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import { UserStore } from '@stores';

@Injectable({
  providedIn: 'root',
})
export class LoggedCheckGuard {
  constructor(private router: Router, private store: Store) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.store.select(UserStore.selectors.selectFeatureLogged).pipe(
      take(1),
      map((logged) => {
        if (logged) {
          return true;
        } else {
          this.router.navigate(['/auth/login'], {
            queryParams: {
              return: state.url,
            },
          });
          return false;
        }
      })
    );
  }
}
