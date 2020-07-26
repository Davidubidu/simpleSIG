import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  failed = false;

  constructor( private auth: AuthService, private router: Router ) { }

  ngOnInit(): void {
  }

  authForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  public tryLogin() {
    const username = this.authForm.get('username').value;
    const password = this.authForm.get('password').value;
    this.auth.login(username, password).then((user) => {
      this.failed = false;
      if (!this.failed) { this.router.navigate(['/app']); }
    }).catch((err) => {
      debugger;
      this.failed = true;
    });
  }
}
