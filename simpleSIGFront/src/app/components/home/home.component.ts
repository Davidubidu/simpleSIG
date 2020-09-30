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
  dataMap: any;

  ngOnInit(): void {

    this.auth.user.subscribe((logged) => {
      this.isLogged = logged;
      this.logedUser = this.auth.getUserInfo().username;
    });

    this.mapPoint.getMapPoints().then((mapPoints, err) => {

      if(err) {
        console.error(err);
      } else {
        this.mapPointsList = mapPoints;
      }
    });

    this.dataMap = { x: 0, y: 0, zoom: 2 };
    
  }

}
