import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ChangeDetectionStrategy } from '@angular/core';
import { AuthService } from '../../services/authService';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly formBuilder = inject(FormBuilder);

  loginForm: FormGroup;
  isLoading = signal<boolean>(false);

  constructor() {
    this.loginForm = this.formBuilder.group({
      login: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    const { login, password } = this.loginForm.value;

    this.authService.login({ login, password }).subscribe({
      next: () => {
        const user = this.authService.getCurrentUser();
        const roles = user?.roles ?? [];
        this.redirectBasedOnRole(roles);
      },
      error: () => {
        this.isLoading.set(false);
      },
    });
  }

  private redirectBasedOnRole(roles: string[]): void {
    if (roles.includes('ROLE_ADMIN')) {
      this.router.navigate(['/admin/dashboard']);
    } else if (roles.includes('ROLE_DISPATCHER')) {
      this.router.navigate(['/dispatcher/dashboard']);
    } else if (roles.includes('ROLE_DRIVER')) {
      this.router.navigate(['/driver/dashboard']);
    } else {
      this.router.navigate(['/']);
    }
  }
}
