import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environments';
import { UserLoginDto } from '../models/dto/request/user/UserLoginDto';
import { UserRegistrationDto } from '../models/dto/request/user/UserRegistrationDto';
import { LoginResponseDto } from '../models/dto/response/user/LoginResponseDto';
import { Router } from '@angular/router';
import { TokenService } from './auth/tokenService';
import { UserStore } from './auth/userStore';
import { UserResponseDto } from '../models/dto/response/user/UserResponseDto';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = `${environment.apiUrl}/users`;
  private router = inject(Router);
  private readonly http = inject(HttpClient);
  private tokenService = inject(TokenService);
  private userStore = inject(UserStore);

  isAuthenticated = this.tokenService.isAuthenticated;
  currentUser = this.userStore.currentUser;

  login(userLoginDto: UserLoginDto): Observable<LoginResponseDto> {
    return this.http
      .post<LoginResponseDto>(`${this.apiUrl}/login`, userLoginDto)
      .pipe(tap((response) => this.handleAuthResponse(response)));
  }

  register(userRegistrationDto: UserRegistrationDto): Observable<LoginResponseDto> {
    return this.http.post<LoginResponseDto>(`${this.apiUrl}/register`, userRegistrationDto);
  }

  logout(): void {
    this.tokenService.clearToken();
    this.userStore.clearUser();
    this.router.navigate(['/login']);
  }
  hasRole(role: string): boolean {
    return this.userStore.hasRole(role);
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated();
  }

  private handleAuthResponse(response: unknown): void {
    const loginResponse = response as LoginResponseDto;
    this.tokenService.setToken(loginResponse.token);
    this.userStore.setUser(loginResponse.user);
  }

  getToken(): string | null {
    return this.tokenService.getToken();
  }

  getCurrentUser(): UserResponseDto | null {
    return this.userStore.getCurrentUser();
  }
}
