import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ChangeDetectionStrategy } from '@angular/core';
import { AuthService } from '../../services/authService';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Navbar {
  readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  logout(): void {
    this.authService.logout();
  }
}
