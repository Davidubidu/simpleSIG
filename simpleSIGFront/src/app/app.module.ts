import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';

// Uncomment if we need to import something from the commented folders
// import {} from '@interceptors';
// import {} from '@services';
import { AdminModule } from '@admin';
import { AuthModule } from '@auth';
import { MapModule } from '@map';
import { SettingsModule } from '@settings';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AdminModule,
    AuthModule,
    MapModule,
    SettingsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
