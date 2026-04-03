import { Injectable, inject, signal, computed } from '@angular/core';
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
  private tokenKey = 'token';
  private userKey = 'user';

  private tokenSignal = signal<string | null>(localStorage.getItem(this.tokenKey));
  private userSignal = signal<UserResponseDto | null>(this.getStoredUser());

  isAuthenticated = computed(() => this.tokenSignal() !== null);
  currentUser = computed(() => this.userSignal());
  isAdmin = computed(() => {
    const user = this.userSignal();
    return user?.roles.includes('ROLE_ADMIN') ?? false;
  });
  isDispatcher = computed(() => {
    const user = this.userSignal();
    return user?.roles.includes('ROLE_DISPATCHER') ?? false;
  });
  isDriver = computed(() => {
    const user = this.userSignal();
    return user?.roles.includes('ROLE_DRIVER') ?? false;
  });

  private readonly http = inject(HttpClient);

  login(userLoginDto: UserLoginDto): Observable<LoginResponseDto> {
    return this.http
      .post<LoginResponseDto>(`${this.apiUrl}/login`, userLoginDto)
      .pipe(tap((response) => this.handleAuthResponse(response)));
  }

  register(userRegistrationDto: UserRegistrationDto): Observable<LoginResponseDto> {
    return this.http.post<LoginResponseDto>(`${this.apiUrl}/register`, userRegistrationDto);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    this.tokenSignal.set(null);
    this.userSignal.set(null);
  }

  getToken(): string | null {
    return this.tokenSignal();
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated();
  }

  getCurrentUser(): UserResponseDto | null {
    return this.currentUser();
  }

  private handleAuthResponse(response: unknown): void {
    const loginResponse = response as LoginResponseDto;
    localStorage.setItem(this.tokenKey, loginResponse.token);
    localStorage.setItem(this.userKey, JSON.stringify(loginResponse.user));
    this.tokenSignal.set(loginResponse.token);
    this.userSignal.set(loginResponse.user);
  }

  private getStoredUser(): UserResponseDto | null {
    const userStr = localStorage.getItem(this.userKey);
    if (userStr) {
      try {
        return JSON.parse(userStr);
      } catch {
        return null;
      }
    }
    return null;
  }
}
