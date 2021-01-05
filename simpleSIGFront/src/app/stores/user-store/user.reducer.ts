import { createReducer, on } from '@ngrx/store';

import { iUser } from '@models';

import {
  login,
  loginSuccess,
  loginError,
  logout,
  logoutSuccess,
} from './user.actions';

export const initialState: iUser = {
  logged: false,
  username: undefined,
  email: undefined,
  roles: undefined,
  errors: undefined,
};

const _userReducer = createReducer(
  getSavedState(),
  on(loginSuccess, (state, { user }) => saveState(user)),
  on(loginError, (state, { error }) => ({ logged: false, error })),
  on(logoutSuccess, (state) => resetState())
);

export function userReducer(state, action) {
  return _userReducer(state, action);
}

function getSavedState() {
  return JSON.parse(localStorage.getItem('savedState'));
}

function saveState(user) {
  localStorage.setItem('savedState', JSON.stringify(user));
  return user;
}

function resetState() {
  localStorage.setItem('savedState', JSON.stringify(initialState));
  return initialState;
}
