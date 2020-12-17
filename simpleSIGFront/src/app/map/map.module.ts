import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MapRoutingModule } from './map-routing.module';
import { MapComponent } from './map.component';
import { Components } from './components';

@NgModule({
  declarations: [MapComponent, ...Components],
  imports: [CommonModule, MapRoutingModule],
})
export class MapModule {}
