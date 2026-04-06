import { Injectable, signal, computed } from '@angular/core';
import { UserResponseDto } from '../../models/dto/response/user/UserResponseDto';

@Injectable({ providedIn: 'root' })
export class UserStore {
  private userKey = 'user';
  private userSignal = signal<UserResponseDto | null>(this.getStoredUser());

  currentUser = computed(() => this.userSignal());

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

  hasRole(role: string): boolean {
    return this.userSignal()?.roles?.includes(role) ?? false;
  }

  setUser(user: UserResponseDto): void {
    localStorage.setItem(this.userKey, JSON.stringify(user));
    this.userSignal.set(user);
  }
  clearUser(): void {
    localStorage.removeItem(this.userKey);
    this.userSignal.set(null);
  }
  getCurrentUser(): UserResponseDto | null {
    return this.currentUser();
  }
}
