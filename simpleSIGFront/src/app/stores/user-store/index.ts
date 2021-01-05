import { login, logout } from './user.actions';
import { userReducer } from './user.reducer';
import { UserEffects } from './user.effects';
import {
  selectFeatureEmail,
  selectFeatureLogged,
  selectFeatureRoles,
  selectFeatureUsername,
  selectFeatureErrors,
} from './user.selectors';

export const store = {
  reducer: userReducer,
  actions: { login, logout },
  effects: UserEffects,
  selectors: {
    selectFeatureEmail,
    selectFeatureLogged,
    selectFeatureRoles,
    selectFeatureUsername,
    selectFeatureErrors,
  },
};
