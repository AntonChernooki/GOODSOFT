import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ChangeDetectionStrategy } from '@angular/core';
import { AuthService } from '../../../services/authService';
import { UserResponseDto } from '../../../models/dto/response/user/UserResponseDto';

@Component({
  selector: 'app-dispatcher-dashboard',
  imports: [RouterLink],
  templateUrl: './dispatcher-dashboard.component.html',
  styleUrls: ['./dispatcher-dashboard.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DispatcherDashboardComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  user = signal<UserResponseDto | null>(null);

  constructor() {
    this.user.set(this.authService.getCurrentUser());
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
