import { createSelector } from '@ngrx/store';

export const selectFeature = (state) => state.user;

export const selectFeatureLogged = createSelector(
  selectFeature,
  (state) => state.logged
);

export const selectFeatureUsername = createSelector(
  selectFeature,
  (state) => state.username
);

export const selectFeatureEmail = createSelector(
  selectFeature,
  (state) => state.email
);

export const selectFeatureRoles = createSelector(
  selectFeature,
  (state) => state.roles
);

export const selectFeatureErrors = createSelector(
  selectFeature,
  (state) => state.errors
);
