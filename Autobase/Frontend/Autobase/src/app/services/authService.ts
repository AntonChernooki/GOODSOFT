import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environments';
import { UserLoginDto } from '../models/dto/request/user/UserLoginDto';
import { UserRegistrationDto } from '../models/dto/request/user/UserRegistrationDto';
import { LoginResponseDto } from '../models/dto/response/user/LoginResponseDto';
import { UserResponseDto } from '../models/dto/response/user/UserResponseDto';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = `${environment.apiUrl}/users`;
  private token = 'token';
  private user = 'user';

  constructor(private http: HttpClient) {}

  login(userLoginDto: UserLoginDto): Observable<LoginResponseDto> {
    return this.http
      .post<LoginResponseDto>(`${this.apiUrl}/login`, userLoginDto)
      .pipe(tap((response) => this.handleAuthResponse(response)));
  }

  register(userRegistgationDto: UserRegistrationDto): Observable<UserResponseDto> {
    return this.http.post<UserResponseDto>(`${this.apiUrl}/register`, userRegistgationDto);
  }

  logout(): void {
    this.clearStorage();
  }

  getToken(): string | null {
    return localStorage.getItem(this.token);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.token);
  }

  isAdmin(): boolean {
    const user = this.getStoredUser();
    if(user?.roles.includes('ROLE_ADMIN')){
      return true;
    }else{
      return false;
    }
  }

  getCurrentUser(): UserResponseDto | null {
    return this.getStoredUser();
  }

  private getStoredUser(): UserResponseDto | null {
    const userStr = localStorage.getItem(this.user);
    if (userStr) {
      try {
        return JSON.parse(userStr);
      } catch {
        return null;
      }
    }
    return null;
  }

  private handleAuthResponse(response: LoginResponseDto): void {
    localStorage.setItem(this.token, response.token);
    localStorage.setItem(this.user, JSON.stringify(response.user));
  }

  private clearStorage(): void {
    localStorage.removeItem(this.token);
    localStorage.removeItem(this.user);
  }
}
