import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatModules } from './mat-design';

import { MainTopbarComponent } from './main-topbar/main-topbar.component';

const Components = [MainTopbarComponent];

@NgModule({
  declarations: [...Components],
  imports: [CommonModule, ...MatModules],
  exports: [...Components],
})
export class ComponentsModule {}
