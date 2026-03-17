import { Injectable } from '@angular/core';
import { UserService } from './user-service';
import { User } from '../models/user';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUser: User | null = null;

  constructor(private userService: UserService) {
    this.restoreUserFromSession();
  }
  private restoreUserFromSession() {
    const login = sessionStorage.getItem('currentUserLogin');
    if (login) {
      const user = this.userService.getUserByLogin(login);
      if (user) {
        this.currentUser = user;
      } else {
        sessionStorage.removeItem('currentUserLogin');
      }
    }
  }

  login(login: string, password: string): boolean {
    const user = this.userService.getUserByLogin(login);

    if (user && user.getPassword() === password) {
      this.currentUser = user;
      sessionStorage.setItem('currentUserLogin', login);
      return true;
    }
    return false;
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }

  changePassword(login: string, oldPassword: string, newPassword: string): boolean {
    if (!this.currentUser || this.currentUser.getLogin() !== login) {
      return false;
    }

    if (this.currentUser.getPassword() !== oldPassword) {
      return false;
    }
    this.currentUser.setPassword(newPassword);

    const updated = this.userService.updatePassword(login, newPassword);
    if (!updated) {
      this.currentUser.setPassword(oldPassword);
      return false;
    }

    return true;
  }

  logout(): void {
    this.currentUser = null;
    sessionStorage.removeItem('currentUserLogin');
  }

  isAuthenticated(): boolean {
    return this.currentUser !== null;
  }
}
