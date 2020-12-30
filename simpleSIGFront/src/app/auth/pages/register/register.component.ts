import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '@services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  failed = false;
  authForm: FormGroup;

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
    });
  }

  public tryRegister() {
    const username = this.authForm.get('username').value;
    const password = this.authForm.get('password').value;
    const email = this.authForm.get('email').value;
    this.auth
      .register(username, password, email)
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
