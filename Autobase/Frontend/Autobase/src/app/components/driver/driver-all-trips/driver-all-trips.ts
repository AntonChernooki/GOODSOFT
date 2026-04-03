import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TripService } from '../../../services/trip/tripService';
import { DriverService } from '../../../services/driver/driverService';
import { AuthService } from '../../../services/authService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-driver-all-trips',
  imports: [CommonModule, RouterLink],
  templateUrl: './driver-all-trips.html',
  styleUrls: ['./driver-all-trips.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DriverAllTripsComponent implements OnDestroy {
  private readonly tripService = inject(TripService);
  private readonly driverService = inject(DriverService);
  private readonly authService = inject(AuthService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  trips = signal<TripResponseDto[]>([]);
  loading = signal<boolean>(false);

  constructor() {
    this.loadAllTrips();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'in_progress':
        return 'status-progress';
      case 'completed':
        return 'status-completed';
      case 'cancelled':
        return 'status-cancelled';
      default:
        return 'status-await';
    }
  }

  loadAllTrips(): void {
    this.loading.set(true);
    const user = this.authService.getCurrentUser();

    if (!user?.id) {
      this.loading.set(false);
      return;
    }

    this.driverService
      .getByUserId(user.id)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (driver) => {
          this.tripService
            .getByDriverId(driver.id)
            .pipe(takeUntil(this.destroySubject))
            .subscribe({
              next: (trips) => {
                this.trips.set(trips);
                this.loading.set(false);
              },
              error: () => {
                this.loading.set(false);
              },
            });
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  getRoute(trip: TripResponseDto): string {
    return `${trip.origin} → ${trip.destination}`;
  }
}

