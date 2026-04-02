import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { catchError, of } from 'rxjs';
import { AuthService } from '../../../../services/authService';
import { DriverService } from '../../../../services/driver/driverService';
import { DriverResponseDto } from '../../../../models/dto/response/driver/DriverResponseDto';
import { UserResponseDto } from '../../../../models/dto/response/user/UserResponseDto';

@Component({
  selector: 'app-driver-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './driver-profile.html',
  styleUrls: ['./driver-profile.css'],
})
export class DriverProfileComponent implements OnInit {
  user: UserResponseDto | null = null;
  driver: DriverResponseDto | null = null;
  loading = false;
  error = false;

  constructor(
    private authService: AuthService,
    private driverService: DriverService,
    private location: Location,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    this.loading = true;
    this.error = false;
    this.cdr.detectChanges();

    this.user = this.authService.getCurrentUser();

    if (!this.user?.id) {
      this.loading = false;
      this.error = true;
      this.cdr.detectChanges();
      return;
    }

    this.driverService
      .getByUserId(this.user.id)
      .pipe(
        catchError((err) => {
          console.error('Ошибка загрузки профиля водителя:', err);
          this.error = true;
          this.loading = false;
          this.cdr.detectChanges();
          return of(null);
        }),
      )
      .subscribe({
        next: (driver) => {
          if (driver) {
            this.driver = driver;
          } else {
            this.error = true;
          }
          this.loading = false;
          this.cdr.detectChanges();
        },
      });
  }

  goBack(): void {
    this.location.back();
  }
}
