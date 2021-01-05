import { createAction, props } from '@ngrx/store';

import { iUser } from '@models';

export const login = createAction(
  '[User Component] Login',
  props<{ username: string; password: string }>()
);

export const loginSuccess = createAction(
  '[User Component] Login success',
  props<{ user: iUser }>()
);

export const loginError = createAction(
  '[User Component] Login error',
  props<{ error: any }>()
);

export const logout = createAction('[User Component] Logout');

export const logoutSuccess = createAction('[User Component] Logout success');
