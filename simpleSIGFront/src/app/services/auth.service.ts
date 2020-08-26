import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import * as bcrypt from 'bcrypt';

const SALT_ROUNDS = 10;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = environment.host;

  constructor(private http: HttpClient, private router: Router) { }

  private authUser = new BehaviorSubject(false);
  public user = this.authUser.asObservable();

  public updateAuth(user: any) {
    const logged = this.saveToken(user);
    this.authUser.next(logged);
  }

  public login(username: string, password: string): any {
    
    const promise = new Promise<any>((resolve, reject) => {
      bcrypt.hash(password, SALT_ROUNDS, function(err, hash) {
        if (err) console.error('Error hashing ' + err);
        this.http.post(`${this.url}/api/auth/signin`, { username, password: hash }).toPromise()
        .then(
          res => {
            this.updateAuth(res);
            resolve(res);
          },
          err => {
            reject(err);
          }
        ); 
      });   
    });

    return promise;
    
  }

  public logout(): any {

    localStorage.removeItem('jwt');
    localStorage.removeItem('user');
    this.authUser.next(false);
    return true;

  }

  public register(username: string, password: string, email: string) {

    const promise = new Promise<any>((resolve, reject) => {
      bcrypt.hash(password, SALT_ROUNDS, function(err, hash) { 
        this.http.post(`${this.url}/api/auth/signup`, { username, password: hash, email, roles: ['user'] }).toPromise()
        .then(
          res => {
            resolve(res);
          },
          err => {
            reject(err);
          }
        );
      });
    });
    
    return promise;
  }

  private saveToken(user: any): boolean {
    if (user.err) { return false; }
    localStorage.setItem('jwt', user.accessToken);
    localStorage.setItem('user', JSON.stringify(user));
    return true;
  }

  public isIdentified() {
    const user = JSON.parse(localStorage.getItem('user'));
    const loggedIn = (user && user.username) ? true : false;
    this.authUser.next(loggedIn);
  }

  public getUserInfo(){
    const user = JSON.parse(localStorage.getItem('user'));
    return user;
  }
}
