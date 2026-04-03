import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ChangeDetectionStrategy } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { AuthService } from '../../services/authService';
import { DriverService } from '../../services/driver/driverService';
import { ErrorModalService } from '../../services/error-modal-service';

@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterComponent {
  private readonly authService = inject(AuthService);
  private readonly driverService = inject(DriverService);
  private readonly errorModalService = inject(ErrorModalService);
  private readonly router = inject(Router);
  private readonly formBuilder = inject(FormBuilder);

  registerForm: FormGroup;
  driverForm: FormGroup;
  isLoading = signal<boolean>(false);
  selectedRole = signal<string>('ROLE_DRIVER');

  constructor() {
    this.registerForm = this.formBuilder.group({
      login: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(4)]],
      confirmPassword: ['', [Validators.required]],
    });

    this.driverForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      experienceYears: [0, [Validators.required, Validators.min(0)]],
      notes: [''],
    });
  }

  onRoleChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.selectedRole.set(target.value);
  }

  async onSubmit(): Promise<void> {
    if (this.registerForm.invalid || (this.selectedRole() === 'ROLE_DRIVER' && this.driverForm.invalid)) {
      this.registerForm.markAllAsTouched();
      this.driverForm.markAllAsTouched();
      return;
    }

    const { password, confirmPassword } = this.registerForm.value;
    if (password !== confirmPassword) {
      this.errorModalService.showError('Пароли не совпадают');
      return;
    }

    this.isLoading.set(true);

    try {
      await firstValueFrom(
        this.authService.register({
          login: this.registerForm.value.login,
          password: this.registerForm.value.password,
          roles: [this.selectedRole()],
        })
      );

      if (this.selectedRole() === 'ROLE_DRIVER') {
        await firstValueFrom(
          this.authService.login({
            login: this.registerForm.value.login,
            password: this.registerForm.value.password,
          })
        );

        const currentUser = this.authService.getCurrentUser();
        if (!currentUser?.id) {
          this.errorModalService.showError('Ошибка: не удалось получить ID пользователя');
          return;
        }

        const driverData = {
          userId: currentUser.id,
          name: this.driverForm.value.name,
          phone: this.driverForm.value.phone,
          experienceYears: this.driverForm.value.experienceYears,
          notes: this.driverForm.value.notes || undefined,
        };

        await firstValueFrom(this.driverService.create(driverData));
      }

      this.errorModalService.showError('Регистрация успешна! Теперь вы можете войти.');
      this.router.navigate(['/login']);
    } catch (err: unknown) {
      const error = err as { error?: { message?: string } };
      const errorMsg = error.error?.message ?? 'Ошибка регистрации';
      this.errorModalService.showError(errorMsg);
    } finally {
      this.isLoading.set(false);
    }
  }
}
