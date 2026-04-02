import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { TripStatus } from '../../../models/enums/TripStatus';
import { AuthService } from '../../../services/authService';
import { CarService } from '../../../services/car/carService';
import { DriverService } from '../../../services/driver/driverService';
import { TripService } from '../../../services/trip/tripService';
import { RepairRequestFormComponent } from '../../repaitRequest/repair-request-form-component/repair-request-form-component';
import { TripMarkFormComponent } from '../../tripMark/trip-mark-form/trip-mark-form';
import { Location } from '@angular/common';

@Component({
  selector: 'app-driver-trips',
  standalone: true,
  imports: [CommonModule, RouterModule, TripMarkFormComponent, RepairRequestFormComponent],
  templateUrl: './driver-trips.html',
  styleUrls: ['./driver-trips.css'],
})
export class DriverTripsComponent implements OnInit {
  activeTrips: (TripResponseDto & { carInfo?: CarResponseDto })[] = [];
  loading = false;
  TripStatus = TripStatus;
  driverId: number | null = null;

  showMarkModal = false;
  showRepairModal = false;
  selectedTripForMark: TripResponseDto | null = null;
  selectedCarIdForRepair: number | null = null;
  

  constructor(
    private tripService: TripService,
    private driverService: DriverService,
    private authService: AuthService,
    private carService: CarService,
    private cdr: ChangeDetectorRef,
    private location:Location,
  ) {}

  ngOnInit(): void {
    this.loadActiveTrips();
  }

  goBack(): void {
    this.location.back();
  }
  loadActiveTrips(): void {
    this.loading = true;
    const user = this.authService.getCurrentUser();

    if (!user?.id) {
      this.loading = false;
      this.cdr.detectChanges();
      return;
    }

    this.driverService.getByUserId(user.id).subscribe({
      next: (driver) => {
        this.driverId = driver.id;
        this.tripService.getByDriverId(driver.id).subscribe({
          next: (trips) => {
            const active = trips.filter(
              (t) => t.status === TripStatus.await || t.status === TripStatus.in_progress
            );

            if (active.length === 0) {
              this.activeTrips = [];
              this.loading = false;
              this.cdr.detectChanges();
              return;
            }

            const carRequests = active.map((trip) =>
              trip.carId
                ? this.carService.getById(trip.carId).pipe(
                    catchError((err) => {
                      console.error(`Ошибка загрузки машины ${trip.carId}:`, err);
                      return of(null);
                    })
                  )
                : of(null)
            );

            forkJoin(carRequests).subscribe({
              next: (cars) => {
                this.activeTrips = active.map((trip, idx) => ({
                  ...trip,
                  carInfo: cars[idx] || undefined,
                }));
                this.loading = false;
                this.cdr.detectChanges();
              },
              error: (err) => {
                console.error('Ошибка в forkJoin:', err);
                this.loading = false;
                this.cdr.detectChanges();
              },
            });
          },
          error: (err) => {
            console.error('Ошибка загрузки рейсов:', err);
            this.loading = false;
            this.cdr.detectChanges();
          },
        });
      },
      error: (err) => {
        console.error('Ошибка загрузки водителя:', err);
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }

  startTrip(tripId: number): void {
    this.tripService.start(tripId).subscribe({
      next: () => this.loadActiveTrips(),
      error: (err) => console.error('Ошибка начала рейса:', err),
    });
  }

  completeTrip(trip: TripResponseDto): void {
    this.tripService.complete(trip.id).subscribe({
      next: () => {
        this.selectedTripForMark = trip;
        this.showMarkModal = true;
        this.loadActiveTrips();
      },
      error: (err) => console.error('Ошибка завершения рейса:', err),
    });
  }

  cancelTrip(tripId: number): void {
    this.tripService.cancel(tripId).subscribe({
      next: () => this.loadActiveTrips(),
      error: (err) => console.error('Ошибка отмены рейса:', err),
    });
  }

  openRepairFromMark(): void {
    if (this.selectedTripForMark?.carId) {
      this.selectedCarIdForRepair = this.selectedTripForMark.carId;
      this.showRepairModal = true;
      this.closeMarkModal();
    }
  }

  closeMarkModal(): void {
    this.showMarkModal = false;
    this.selectedTripForMark = null;
  }

  closeRepairModal(): void {
    this.showRepairModal = false;
    this.selectedCarIdForRepair = null;
  }

  getRoute(trip: TripResponseDto): string {
    return `${trip.origin} → ${trip.destination}`;
  }
}