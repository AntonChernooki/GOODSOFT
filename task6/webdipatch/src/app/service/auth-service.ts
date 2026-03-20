import { Injectable } from '@angular/core';
import { UserService } from './user-service';
import { User } from '../models/user';
import { catchError, map, Observable, of, switchMap, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UserMapper } from '../mappers/UserMapper';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUser: User | null = null;
  private authUrl = 'http://localhost:8081/webdipatch/api/auth';

  private tokenKey = 'auth_token';
  private loginKey = 'currentUserLogin';

  constructor(
    private userService: UserService,
    private httpClient: HttpClient,
  ) {}

  private setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  private setCurrentUserLogin(login: string): void {
    localStorage.setItem(this.loginKey, login);
  }

  login(login: string, password: string): Observable<boolean> {
    return this.httpClient.post<any>(`${this.authUrl}/login`, { login, password }).pipe(
      tap((response) => {
        this.setToken(response.token);
        this.setCurrentUserLogin(response.login);
      }),
      switchMap(() => this.userService.getUserByLogin(login)),
      tap((user) => {
        this.currentUser = user;
      }),
      map(() => true),
      catchError((err) => {
        console.error('Ошибка входа', err);
        return of(false);
      }),
    );
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }

  changePassword(login: string, oldPassword: string, newPassword: string): Observable<boolean> {
    const body = { login, oldPassword, newPassword };
    return this.httpClient
      .post(`${this.authUrl}/change-password`, body, { observe: 'response' })
      .pipe(
        map((response) => response.status === 200),
        tap((success) => {
          if (success && this.currentUser && this.currentUser.getLogin() === login) {
            this.currentUser.setPassword(newPassword);
          }
        }),
        catchError((err) => {
          console.error('Ошибка смены пароля', err);
          return of(false);
        }),
      );
  }

  logout(): void {
    this.currentUser = null;
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.loginKey);
  }

  isAuthenticated(): boolean {
    return this.currentUser !== null;
  }

  restoreUserFromSession(): Promise<boolean> {
    const token = localStorage.getItem(this.tokenKey);
    const login = localStorage.getItem(this.loginKey);
    if (login && token) {
      return this.userService
        .getUserByLogin(login)
        .toPromise()
        .then((user) => {
          if (user) {
            this.currentUser = user;
            return true;
          } else {
            this.logout();
            return false;
          }
        })
        .catch((err) => {
          console.error('Ошибка восстановления', err);
          this.logout();
          return false;
        });
    }
    return Promise.resolve(false);
  }
}
