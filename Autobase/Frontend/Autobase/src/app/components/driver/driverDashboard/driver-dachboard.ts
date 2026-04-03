import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ChangeDetectionStrategy } from '@angular/core';
import { AuthService } from '../../../services/authService';
import { UserResponseDto } from '../../../models/dto/response/user/UserResponseDto';

@Component({
  selector: 'app-driver-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './driver-dachboard.html',
  styleUrls: ['./driver-dachboard.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DriverDachboard implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);

  user = signal<UserResponseDto | null>(null);

  ngOnInit(): void {
    this.user.set(this.authService.getCurrentUser());
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
