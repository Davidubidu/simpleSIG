import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private auth: AuthService) { }

  logedUser: string;
  isLogged: boolean;

  ngOnInit(): void {

    this.auth.user.subscribe((logged) => {
      this.isLogged = logged;
      this.logedUser = this.auth.getUserInfo().username;
    });
    
  }

}
