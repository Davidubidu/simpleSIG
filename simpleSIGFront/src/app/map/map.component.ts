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
