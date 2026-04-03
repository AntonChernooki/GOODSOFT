import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TripService } from '../../../services/trip/tripService';
import { DriverService } from '../../../services/driver/driverService';
import { CarService } from '../../../services/car/carService';
import { AuthService } from '../../../services/authService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject, firstValueFrom } from 'rxjs';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { TripStatus } from '../../../models/enums/TripStatus';
import { Location } from '@angular/common';
import { RepairRequestFormComponent } from '../../repaitRequest/repair-request-form-component/repair-request-form-component';
import { TripMarkFormComponent } from '../../tripMark/trip-mark-form/trip-mark-form';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-driver-trips',
  imports: [CommonModule, RouterLink, TripMarkFormComponent, RepairRequestFormComponent],
  templateUrl: './driver-trips.html',
  styleUrls: ['./driver-trips.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DriverTripsComponent implements OnDestroy {
  private readonly tripService = inject(TripService);
  private readonly driverService = inject(DriverService);
  private readonly carService = inject(CarService);
  private readonly authService = inject(AuthService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  activeTrips = signal<(TripResponseDto & { carInfo?: CarResponseDto })[]>([]);
  loading = signal<boolean>(false);
  driverId = signal<number | null>(null);

  showMarkModal = signal<boolean>(false);
  showRepairModal = signal<boolean>(false);
  selectedTripForMark = signal<TripResponseDto | null>(null);
  selectedCarIdForRepair = signal<number | null>(null);

  readonly TripStatus = TripStatus;

  constructor() {
    this.loadActiveTrips();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  async loadActiveTrips(): Promise<void> {
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
        next: async (driver) => {
          this.driverId.set(driver.id);
          this.tripService
            .getByDriverId(driver.id)
            .pipe(takeUntil(this.destroySubject))
            .subscribe({
              next: async (trips) => {
                const active = trips.filter(
                  (t) => t.status === this.TripStatus.await || t.status === this.TripStatus.in_progress
                );

                if (active.length === 0) {
                  this.activeTrips.set([]);
                  this.loading.set(false);
                  return;
                }

                const carObservables = active.map((trip) => {
                  if (trip.carId) {
                    return this.carService.getById(trip.carId);
                  }
                  return null;
                });

                const carPromises = carObservables.map((observable) => {
                  if (observable) {
                    return firstValueFrom(observable);
                  }
                  return Promise.resolve(null);
                });

                try {
                  const cars = await Promise.all(carPromises);
                  const tripsWithCars = active.map((trip, index) => ({
                    ...trip,
                    carInfo: cars[index] || undefined,
                  }));
                  this.activeTrips.set(tripsWithCars);
                  this.loading.set(false);
                } catch {
                  this.loading.set(false);
                }
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

  startTrip(tripId: number): void {
    this.tripService
      .start(tripId)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => this.loadActiveTrips(),
        error: (error) => console.error('Ошибка начала рейса:', error),
      });
  }

  completeTrip(trip: TripResponseDto): void {
    this.tripService
      .complete(trip.id)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          this.selectedTripForMark.set(trip);
          this.showMarkModal.set(true);
        },
        error: (error) => console.error('Ошибка завершения рейса:', error),
      });
  }

  cancelTrip(tripId: number): void {
    this.tripService
      .cancel(tripId)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => this.loadActiveTrips(),
        error: (error) => console.error('Ошибка отмены рейса:', error),
      });
  }

  openRepairFromMark(): void {
    const trip = this.selectedTripForMark();
    if (trip?.carId) {
      this.selectedCarIdForRepair.set(trip.carId);
      this.showRepairModal.set(true);
    }
  }

  closeMarkModal(): void {
    this.showMarkModal.set(false);
    this.selectedTripForMark.set(null);
  }

  closeRepairModal(): void {
    this.showRepairModal.set(false);
    this.selectedCarIdForRepair.set(null);
  }

  getRoute(trip: TripResponseDto): string {
    return `${trip.origin} → ${trip.destination}`;
  }
}

