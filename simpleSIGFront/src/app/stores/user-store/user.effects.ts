import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';
import { EMPTY, of } from 'rxjs';

import {
  login,
  loginSuccess,
  loginError,
  logout,
  logoutSuccess,
} from './user.actions';
import { AuthService } from '@services';
import { iUser } from '@models';

@Injectable()
export class UserEffects {
  public login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(login),
      mergeMap(({ username, password }) =>
        this.authService.login(username, password).pipe(
          map((user: iUser) => loginSuccess({ user })),
          catchError((error) => of(loginError({ error })))
        )
      )
    )
  );

  public logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(logout),
      tap(() => {
        this.authService.logout();
      }),
      map(() => logoutSuccess())
    )
  );

  constructor(private actions$: Actions, private authService: AuthService) {}
}
