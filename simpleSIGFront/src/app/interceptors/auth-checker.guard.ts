import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LoggedCheckGuard {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (localStorage.getItem('user')) {
      return true;
    } else {
      this.router.navigate(['/auth/login'], {
        queryParams: {
          return: state.url,
        },
      });
      return false;
    }
  }
}
