import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guards';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./components/login/login').then((m) => m.LoginComponent),
  },
  {
    path: 'registration',
    loadComponent: () => import('./components/register/register').then((m) => m.RegisterComponent),
  },

  {
    path: 'driver',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_DRIVER'] },
    children: [
      {
        path: 'profile',
        loadComponent: () =>
          import('./components/driver/driver-profile/driver-profile/driver-profile').then(
            (c) => c.DriverProfileComponent,
          ),
      },

      {
        path: 'dashboard',
        loadComponent: () =>
          import('./components/driver/driverDashboard/driver-dachboard').then(
            (c) => c.DriverDachboard,
          ),
      },
      {
        path: 'trips',
        loadComponent: () =>
          import('./components/driver/driver-trips/driver-trips').then(
            (c) => c.DriverTripsComponent,
          ),
      },
      {
        path: 'all-trips',
        loadComponent: () =>
          import('./components/driver/driver-all-trips/driver-all-trips').then(
            (c) => c.DriverAllTripsComponent,
          ),
      },
    ],
  },

  {
    path: 'cars/details/:id',
    canActivate: [AuthGuard],
    loadComponent: () =>
      import('./components/car/car-details/car-details').then((c) => c.CarDetailsComponent),
  },

  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: '/login',
  },
];
