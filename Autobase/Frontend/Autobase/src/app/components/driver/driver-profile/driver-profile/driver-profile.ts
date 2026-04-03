import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { AuthService } from '../../../../services/authService';
import { DriverService } from '../../../../services/driver/driverService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { DriverResponseDto } from '../../../../models/dto/response/driver/DriverResponseDto';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-driver-profile',
  imports: [CommonModule],
  templateUrl: './driver-profile.html',
  styleUrls: ['./driver-profile.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DriverProfileComponent implements OnDestroy {
  private readonly authService = inject(AuthService);
  private readonly driverService = inject(DriverService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  driver = signal<DriverResponseDto | null>(null);
  loading = signal<boolean>(false);
  error = signal<boolean>(false);

  constructor() {
    this.loadProfile();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  loadProfile(): void {
    this.loading.set(true);
    this.error.set(false);

    const user = this.authService.getCurrentUser();
    if (!user?.id) {
      this.loading.set(false);
      this.error.set(true);
      return;
    }

    this.driverService
      .getByUserId(user.id)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (driver) => {
          this.driver.set(driver);
          this.loading.set(false);
        },
        error: () => {
          this.error.set(true);
          this.loading.set(false);
        },
      });
  }

  goBack(): void {
    this.location.back();
  }
}

