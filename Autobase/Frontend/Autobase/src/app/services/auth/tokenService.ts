import { Injectable, signal, computed } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class TokenService {
  private tokenKey = 'token';

  private tokenSignal = signal<string | null>(localStorage.getItem(this.tokenKey));
  isAuthenticated = computed(() => this.tokenSignal() !== null);

  getToken(): string | null {
    return this.tokenSignal();
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.tokenSignal.set(token);
  }
  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
    this.tokenSignal.set(null);
  }
}
