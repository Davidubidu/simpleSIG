import { Component, OnInit } from '@angular/core';
import { AuthService } from '@services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'simpleSIGFront';

  constructor(private auth: AuthService) {}

  logedUser: string;
  isLogged: boolean;

  ngOnInit(): void {
    // FIXME: Change this to future User.store or similar
    this.auth.user.subscribe((logged) => {
      this.isLogged = logged;
      this.logedUser = this.auth.getUserInfo().username;
    });
  }
}
