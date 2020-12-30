import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AuthRoutingModule } from './auth-routing.module';
import { Pages } from './pages';

const Modules = [
  CommonModule,
  AuthRoutingModule,
  FormsModule,
  ReactiveFormsModule,
];

@NgModule({
  declarations: [...Pages],
  imports: [...Modules],
})
export class AuthModule {}
