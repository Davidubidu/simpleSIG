import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoggedCheckGuard } from './interceptors';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'auth',
        canActivate: [],
        loadChildren: () =>
          import('@auth/auth.module').then((m) => m.AuthModule),
      },
      {
        path: 'map',
        canActivate: [LoggedCheckGuard],
        loadChildren: () => import('@map/map.module').then((m) => m.MapModule),
      },
      {
        path: 'settings',
        canActivate: [LoggedCheckGuard],
        loadChildren: () =>
          import('@settings/settings.module').then((m) => m.SettingsModule),
      },
      {
        path: 'admin',
        canActivate: [LoggedCheckGuard],
        loadChildren: () =>
          import('@admin/admin.module').then((m) => m.AdminModule),
      },
      {
        path: '**',
        pathMatch: 'full',
        redirectTo: 'map',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
