import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})



export class ErrorModalService {
  isVisible = signal(false);
  errorMessage = signal('');

  showError(message: string): void {
    this.errorMessage.set(message);
    this.isVisible.set(true);
  }

  hideError(): void {
    this.isVisible.set(false);
    this.errorMessage.set('');
  }
}