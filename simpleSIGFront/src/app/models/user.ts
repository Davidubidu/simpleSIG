export interface iUser {
  logged: boolean;
  username?: string;
  email?: string;
  roles?: string[];
  errors?: any;
}
