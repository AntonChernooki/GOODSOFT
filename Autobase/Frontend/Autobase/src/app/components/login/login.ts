import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/authService';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class LoginComponent {
  login = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login({ login: this.login, password: this.password }).subscribe({
      next: () => {
        const user = this.authService.getCurrentUser();
        const roles = user?.roles || [];
        this.redirectBasedOnRole(roles);
      },
      error: (err) => {
        console.error('Ошибка входа', err);
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