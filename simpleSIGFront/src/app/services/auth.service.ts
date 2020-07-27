import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';

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
      /*
      this.http.post(`${this.url}/user/login`, { username, password }).toPromise()
      .then(
        res => {
          this.updateAuth(res);
          resolve(res);
        },
        err => {
          reject(err);
        }
      );  
      */   
     resolve(true);
    });

    //return promise;
    
    // TODO: DELETE THIS 2 lines when BACKEND is working
    this.updateAuth({username});
    return promise;
  }

  public logout(): any {

    /*
    const promise = new Promise<any>((resolve, reject) => {

      this.updateAuth(false);

      this.http.get(`${this.url}/user/logout`).toPromise()
      .then(
        res => {
          resolve(res);
        },
        err => {
          reject(err);
        }
      );
    });
    */
    localStorage.removeItem('token');
    this.authUser.next(false);
    // TODO: DELETE THIS line when BACKEND is working
    return true;
    //return promise;
  }

  public register(username: string, password: string) {

    const promise = new Promise<any>((resolve, reject) => {

      this.http.post(`${this.url}/user`, { username, password }).toPromise()
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
    
    return promise;
  }

  private saveToken(user: any): boolean {
    if (user.err) { return false; }
    localStorage.setItem('token', JSON.stringify(user));
    return true;
  }

  public isIdentified() {
    const user = JSON.parse(localStorage.getItem('token'));
    const loggedIn = (user && user.username) ? true : false;
    this.authUser.next(loggedIn);
  }

  public getUserInfo(){
    const user = JSON.parse(localStorage.getItem('token'));
    return user;
  }
}
