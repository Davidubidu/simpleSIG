import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import { UserStore } from '@stores';
import { iUser } from '@models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  user$: Observable<iUser>;
  title = 'simpleSIGFront';
  subscription: Subscription[];

  username$: Observable<string>;
  logged$: Observable<boolean>;

  constructor(
    private userStore: Store<{ user: iUser }>,
    private router: Router
  ) {
    this.user$ = userStore.select('user');
  }

  ngOnInit(): void {
    this.logged$ = this.userStore.pipe(
      select(UserStore.selectors.selectFeatureLogged)
    );

    this.username$ = this.userStore.pipe(
      select(UserStore.selectors.selectFeatureUsername)
    );
  }

  login() {
    this.router.navigate(['/auth/login']);
  }

  logout() {
    this.userStore.dispatch(UserStore.actions.logout());
    this.router.navigate(['/auth/login']);
  }
}
