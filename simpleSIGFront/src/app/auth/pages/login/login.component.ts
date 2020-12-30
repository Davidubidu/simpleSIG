import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '@services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  failed = false;
  authForm: FormGroup;

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  public tryLogin() {
    const username = this.authForm.get('username').value;
    const password = this.authForm.get('password').value;
    this.auth
      .login(username, password)
      .then((user) => {
        this.failed = false;
        if (!this.failed) {
          this.router.navigate(['/app']);
        }
      })
      .catch((err) => {
        this.failed = true;
      });
  }
}
