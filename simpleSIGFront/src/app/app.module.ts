import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';

// Uncomment if we need to import something from the commented folders
// import {} from '@services';
import { AuthInterceptorProviders } from '@interceptors';
import { AdminModule } from '@admin';
import { AuthModule } from '@auth';
import { MapModule } from '@map';
import { SettingsModule } from '@settings';
import { ComponentsModule } from '@components';
import { UserStore } from '@stores';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

const Stores = { user: UserStore.reducer };
const Effects = UserStore.effects;

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
    ComponentsModule,
    BrowserAnimationsModule,
    StoreModule.forRoot(Stores),
    EffectsModule.forRoot([Effects]),
  ],
  providers: [AuthInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
