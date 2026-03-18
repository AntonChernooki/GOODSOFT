import { Injectable } from '@angular/core';
import { UserService } from './user-service';
import { User } from '../models/user';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UserMapper } from '../mappers/UserMapper';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUser: User | null = null;
  private authUrl = 'http://localhost:8081/webdipatch/api/auth';
  constructor(
    private userService: UserService,
    private httpClient: HttpClient,
  ) {
    this.restoreUserFromSession();
  }
  private restoreUserFromSession() {
    const login = sessionStorage.getItem('currentUserLogin');
    if (login) {
      const user = this.userService.getUserByLogin(login).subscribe({
        next: (user) => {
          this.currentUser = user;
        },
        error: (err) => {
          console.log('не получилось найти пользователя по логину');
          console.log(err);
          sessionStorage.removeItem('currentUserLogin');
        },
      });
    }
  }

  login(login: string, password: string): Observable<boolean> {
    return this.httpClient.post<any>(`${this.authUrl}/login`, { login, password }).pipe(
      map((response) => UserMapper.toUser(response)),
      tap((user) => {
        this.currentUser = user;
        sessionStorage.setItem('currentUserLogin', login);
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
    return this.httpClient.post(`${this.authUrl}/loginedit`, body, { observe: 'response' }).pipe(
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
    sessionStorage.removeItem('currentUserLogin');
  }

  isAuthenticated(): boolean {
    return this.currentUser !== null;
  }
}
