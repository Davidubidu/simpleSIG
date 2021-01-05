import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

import { UserStore } from '@stores';
import { iUser } from '@models';

const SALT_ROUNDS = 10;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url = environment.host;

  constructor(
    private http: HttpClient,
    private userStore: Store<{ user: iUser }>
  ) {}

  public login(username: string, password: string): Observable<iUser> {
    return this.http
      .post(`${this.url}/api/auth/login`, { username, password })
      .pipe(
        tap((user) => this.updateAuth(user)),
        map((user: any) => ({
          logged: true,
          username: user.username,
          email: user.email,
          roles: user.roles,
        }))
      );
  }

  public logout() {
    localStorage.removeItem('jwt');
  }

  public register(username: string, password: string, email: string) {
    const promise = new Promise<any>((resolve, reject) => {
      this.http
        .post(`${this.url}/api/auth/register`, {
          username,
          password,
          email,
          roles: ['user'],
        })
        .toPromise()
        .then(
          (res) => {
            resolve(res);
          },
          (err) => {
            reject(err);
          }
        );
    });

    return promise;
  }

  private updateAuth(loggedUser: any) {
    const user: iUser = {
      logged: true,
      username: loggedUser.username,
      email: loggedUser.email,
      roles: loggedUser.roles,
    };

    //this.userStore.dispatch(UserStore.actions.login({ user }));

    this.saveToken(loggedUser);
  }

  private saveToken(user: any) {
    if (user.err) {
      return false;
    }
    localStorage.setItem('jwt', user.accessToken);
  }
}
