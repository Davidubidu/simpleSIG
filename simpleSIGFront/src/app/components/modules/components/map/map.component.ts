import { Component, Input, OnInit } from '@angular/core';
import { Map}  from 'ol/Map';
import { OSM } from 'ol/source/OSM';
import { TileLayer } from 'ol/layer/Tile';
import { View } from 'ol/View';

@Component({
  selector: 'app-mod-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  @Input() data: any;
  constructor() { }

  map: Map;

  ngOnInit(): void {
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }) ],
      target: 'map',
      view: new View({
        center: [this.data.x, this.data.y],
        zoom: this.data.zoom,
      }),
    });
  }

  
  zoomOut() {
    var view = this.map.getView();
    var zoom = view.getZoom();
    view.setZoom(zoom - 1);
  };
  
  zoomIn() {
    var view = this.map.getView();
    var zoom = view.getZoom();
    view.setZoom(zoom + 1);
  };

}
