import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./components/pages/login/login').then((m) => m.LoginComponent),
  },
  {
    path: 'welcome',
    loadComponent: () => import('./components/pages/welcome/welcome').then((m) => m.WelcomeComponent),
    canActivate: [AuthGuard],
  },
  {
    path: 'userlist',
    loadComponent: () => import('./components/pages/user-list/user-list').then((m) => m.UserListComponent),
    canActivate: [AuthGuard,RoleGuard],
     data:{roles: [Role.ADMIN]}
  },
  {
    path: 'useredit/:login',
    loadComponent: () => import('./components/pages/user-edit/user-edit').then((m) => m.UserEditComponent),
    canActivate: [AuthGuard,RoleGuard],
    data:{roles: [Role.ADMIN]}
  },
  {
    path: 'loginedit',
    loadComponent: () =>
      import('./components/pages/change-password/change-password').then((m) => m.ChangePasswordComponent),
    canActivate: [AuthGuard],
  },
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: '**', redirectTo: '/welcome' },
];
