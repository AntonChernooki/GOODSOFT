import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { AuthService } from '../../services/authService';
import { DriverService } from '../../services/driver/driverService';
import { ErrorModalService } from '../../services/error-modal-service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class RegisterComponent {
  login = '';
  password = '';
  confirmPassword = '';
  role: string = 'ROLE_DRIVER';

  driverName = '';
  driverPhone = '';
  driverExperienceYears: number = 0;
  driverNotes = '';

  loading = false;

  constructor(
    private authService: AuthService,
    private driverService: DriverService,
    private errorModalService: ErrorModalService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  async onSubmit(): Promise<void> {
    // Валидация
    if (!this.login || !this.password || !this.confirmPassword) {
      this.errorModalService.showError('Все поля обязательны для заполнения');
      return;
    }
    if (this.password !== this.confirmPassword) {
      this.errorModalService.showError('Пароли не совпадают');
      return;
    }
    if (this.role === 'ROLE_DRIVER') {
      if (!this.driverName || !this.driverPhone || this.driverExperienceYears <= 0) {
        this.errorModalService.showError('Заполните все данные водителя');
        return;
      }
    }

    this.loading = true;
    this.cdr.detectChanges();

    try {
      // 1. Регистрация пользователя
const loginResponse = await firstValueFrom(
  this.authService.register({
    login: this.login,
    password: this.password,
    roles: [this.role],
  })
);
const user = loginResponse.user; // теперь user – это UserResponseDto
console.log('Пользователь создан:', user);

if (this.role === 'ROLE_DRIVER') {
  // 2. Автоматический вход
  await firstValueFrom(
    this.authService.login({ login: this.login, password: this.password })
  );
  console.log('Автовход выполнен, токен сохранён');

  // 3. Создание профиля водителя
  const driverData = {
    userId: user.id,     // теперь корректный ID
    name: this.driverName,
    phone: this.driverPhone,
    experienceYears: this.driverExperienceYears,
    notes: this.driverNotes || undefined,
  };
  console.log('Отправляем данные водителя:', driverData);

  await firstValueFrom(this.driverService.create(driverData));
  console.log('Водитель создан');
}

      this.loading = false;
      this.cdr.detectChanges();
      this.errorModalService.showError('Регистрация успешна! Теперь вы можете войти.');
      this.router.navigate(['/login']);
    } catch (err: any) {
      this.loading = false;
      this.cdr.detectChanges();
      console.error('Ошибка регистрации:', err);
      const errorMsg = err.error?.message || 'Ошибка регистрации';
      this.errorModalService.showError(errorMsg);
    }
  }
}