import { Routes } from '@angular/router';
import { canActivateAuth } from './guards/auth.guard';
import { canActivateRole } from './guards/role.guards';

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
    canActivate: [canActivateAuth, canActivateRole],
    data: { roles: ['ROLE_DRIVER'] },
    children: [
      {
        path: 'profile',
        loadComponent: () =>
          import('./components/driver/driver-profile/driver-profile/driver-profile').then(
            (c) => c.DriverProfileComponent
          ),
      },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./components/driver/driverDashboard/driver-dachboard').then(
            (c) => c.DriverDachboard
          ),
      },
      {
        path: 'trips',
        loadComponent: () =>
          import('./components/driver/driver-trips/driver-trips').then((c) => c.DriverTripsComponent),
      },
      {
        path: 'all-trips',
        loadComponent: () =>
          import('./components/driver/driver-all-trips/driver-all-trips').then(
            (c) => c.DriverAllTripsComponent
          ),
      },
      {
        path: 'repair-requests',
        loadComponent: () =>
          import('./components/driver/driver-repair-requests/driver-repair-requests.component').then(
            (c) => c.DriverRepairRequestsComponent
          ),
      },
    ],
  },
  {
    path: 'dispatcher',
    canActivate: [canActivateAuth, canActivateRole],
    data: { roles: ['ROLE_DISPATCHER'] },
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./components/dispatcher/dispatcher-dashboard/dispatcher-dashboard.component').then(
            (c) => c.DispatcherDashboardComponent
          ),
      },
      {
        path: 'trips',
        loadComponent: () =>
          import('./components/dispatcher/dispatcher-trips/dispatcher-trips.component').then(
            (c) => c.DispatcherTripsComponent
          ),
      },
      {
        path: 'drivers',
        loadComponent: () =>
          import('./components/dispatcher/dispatcher-drivers/dispatcher-drivers.component').then(
            (c) => c.DispatcherDriversComponent
          ),
      },
      {
        path: 'cars',
        loadComponent: () =>
          import('./components/dispatcher/dispatcher-cars/dispatcher-cars.component').then(
            (c) => c.DispatcherCarsComponent
          ),
      },
      {
        path: 'repair-requests',
        loadComponent: () =>
          import('./components/dispatcher/dispatcher-repair-requests/dispatcher-repair-requests.component').then(
            (c) => c.DispatcherRepairRequestsComponent
          ),
      },
    ],
  },
  {
    path: 'cars/details/:id',
    canActivate: [canActivateAuth],
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
