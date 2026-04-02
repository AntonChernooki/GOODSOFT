import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserResponseDto } from '../../../models/dto/response/user/UserResponseDto';
import { AuthService } from '../../../services/authService';


@Component({
  selector: 'app-driver-dachboard',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './driver-dachboard.html',
  styleUrl: './driver-dachboard.css',
})
export class DriverDachboard {

  user: UserResponseDto | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
