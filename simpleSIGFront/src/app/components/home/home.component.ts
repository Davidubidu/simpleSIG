import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { MapPointService } from '../../services/map-point.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private auth: AuthService, private mapPoint: MapPointService) { }

  logedUser: string;
  isLogged: boolean;
  mapPointsList = [];

  ngOnInit(): void {

    this.auth.user.subscribe((logged) => {
      this.isLogged = logged;
      this.logedUser = this.auth.getUserInfo().username;
    });

    this.mapPoint.getMapPoints().then((mapPoints) => {
      this.mapPointsList = mapPoints;
    });
    
  }

}
