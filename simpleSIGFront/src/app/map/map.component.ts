import { Component, OnInit } from '@angular/core';

import { AuthService, MapPointService } from '@services';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements OnInit {
  constructor(private auth: AuthService, private mapPoint: MapPointService) {}

  logedUser: string;
  isLogged: boolean;
  mapPointsList = [];
  dataMap: any;

  ngOnInit(): void {
    // FIXME: Change this to future User.store or similar
    this.auth.user.subscribe((logged) => {
      this.isLogged = logged;
      this.logedUser = this.auth.getUserInfo().username;
    });

    this.mapPoint.getMapPoints().then((mapPoints, err) => {
      if (err) {
        console.error(err);
      } else {
        this.mapPointsList = mapPoints;
      }
    });

    this.dataMap = { x: 0, y: 0, zoom: 2 };
  }
}
