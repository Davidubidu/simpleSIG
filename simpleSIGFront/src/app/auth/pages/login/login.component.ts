import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { skip } from 'rxjs/operators';

import { iUser } from '@models';
import { UserStore } from '@stores';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  failed = false;
  authForm: FormGroup;
  error: Subscription;
  logged: Subscription;

  subscription: Subscription[] = [];

  constructor(
    private userStore: Store<{ user: iUser }>,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userStore.dispatch(UserStore.actions.logout());

    this.authForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });

    this.logged = this.userStore
      .pipe(select(UserStore.selectors.selectFeatureLogged), skip(1))
      .subscribe((isLogged: boolean) => {
        this.failed = false;
        if (!this.failed) {
          this.router.navigate(['/app']);
        }
      });

    this.error = this.userStore
      .pipe(select(UserStore.selectors.selectFeatureErrors), skip(1))
      .subscribe((error: any) => {
        if (error) {
          this.failed = true;
        }
      });

    this.subscription.push(this.logged);

    this.subscription.push(this.error);
  }

  public tryLogin() {
    const username = this.authForm.get('username').value;
    const password = this.authForm.get('password').value;

    this.userStore.dispatch(UserStore.actions.login({ username, password }));
  }

  ngOnDestroy() {
    this.subscription.forEach((eachSubscription) => {
      eachSubscription.unsubscribe();
    });
  }
}
