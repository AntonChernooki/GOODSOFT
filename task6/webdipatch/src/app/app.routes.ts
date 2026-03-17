import { Routes } from '@angular/router';
import { LoginComponent } from './components/pages/login/login';
import { UserEditComponent } from './components/pages/user-edit/user-edit';
import { UserListComponent } from './components/pages/user-list/user-list';
import { ChangePasswordComponent } from './components/pages/change-password/change-password';
import { WelcomeComponent } from './components/pages/welcome/welcome';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { Role } from './models/role';

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
