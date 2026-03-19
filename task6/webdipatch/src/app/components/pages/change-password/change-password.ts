import { Component, signal } from '@angular/core';
import { AuthService } from '../../../service/auth-service';
import { RouterLink } from '@angular/router';
import { User } from '../../../models/user';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-change-password',
  imports: [RouterLink, FormsModule, TranslateModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css',
})
export class ChangePasswordComponent {
  oldPassword = '';
  newPassword = '';

  successMessage = signal<string>('');
  errorMessage = signal<string>('');

  currentUser: User | null = null;

  private destoy = new Subject<void>();

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
  }

  ngOnDestroy(): void {
    this.destoy.next();
    this.destoy.complete();
  }

  onChangePassword(form: NgForm): void {
    this.successMessage.set('');
    this.errorMessage.set('');

    if (form.invalid) {
      this.errorMessage.set('FILL_ALL_FIELDS');
      return;
    }

    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      this.errorMessage.set('USER_NOT_AUTHORIZED');
      return;
    }

    this.authService
      .changePassword(currentUser.getLogin(), this.oldPassword, this.newPassword)
      .pipe(takeUntil(this.destoy))
      .subscribe({
        next: (success) => {
          if (success) {
            this.successMessage.set('PASSWORD_CHANGED_SUCCESS');
            this.oldPassword = '';
            this.newPassword = '';
            form.resetForm();
          } else {
            this.errorMessage.set('INVALID_OLD_PASSWORD');
          }
        },
        error: (err) => {
          console.error('Ошибка при смене пароля:', err);
          this.errorMessage.set('PASSWORD_CHANGE_ERROR');
        },
      });
  }
}
