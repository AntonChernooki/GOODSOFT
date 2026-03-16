import { Routes } from '@angular/router';
import { Login } from './components/pages/login/login';
import { UserEdit } from './components/pages/user-edit/user-edit';
import { UserList } from './components/pages/user-list/user-list';
import { ChangePassword } from './components/pages/change-password/change-password';
import { Welcome } from './components/pages/welcome/welcome';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'useredit', component: UserEdit },
  { path: 'welcome', component: Welcome },
  { path: 'userlist', component: UserList },
  { path: 'loginedit', component: ChangePassword },

  { path: 'useredit/:login', component: UserEdit },

  { path: '', component: Welcome },
  { path: '**', component: Welcome },
];
