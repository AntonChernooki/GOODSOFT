import { Component, inject, signal, computed, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TripService } from '../../../services/trip/tripService';
import { DriverService } from '../../../services/driver/driverService';
import { CarService } from '../../../services/car/carService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { DriverResponseDto } from '../../../models/dto/response/driver/DriverResponseDto';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { TripCreateDto } from '../../../models/dto/request/trip/TripCreateDto';
import { TripAssignDto } from '../../../models/dto/request/trip/TripAssignDto';
import { TripStatus } from '../../../models/enums/TripStatus';
import { DriverStatus } from '../../../models/enums/DriverStatus';
import { CarStatus } from '../../../models/enums/CarStatus';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-dispatcher-trips',
  imports: [CommonModule, FormsModule],
  templateUrl: './dispatcher-trips.component.html',
  styleUrls: ['./dispatcher-trips.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DispatcherTripsComponent implements OnDestroy {
  private readonly tripService = inject(TripService);
  private readonly driverService = inject(DriverService);
  private readonly carService = inject(CarService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  trips = signal<TripResponseDto[]>([]);
  drivers = signal<DriverResponseDto[]>([]);
  cars = signal<CarResponseDto[]>([]);
  loading = signal<boolean>(false);

  availableDrivers = computed(() =>
    this.drivers().filter((d) => d.status === DriverStatus.active)
  );

  availableCars = computed(() =>
    this.cars().filter((c) => c.status === CarStatus.available)
  );

  showCreateModal = signal<boolean>(false);
  showAssignModal = signal<boolean>(false);
  selectedTrip = signal<TripResponseDto | null>(null);

  newTripOrigin = signal<string>('');
  newTripDestination = signal<string>('');
  assignDriverId = signal<number>(0);
  assignCarId = signal<number>(0);

  readonly TripStatus = TripStatus;

  constructor() {
    this.loadTrips();
    this.loadDrivers();
    this.loadCars();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  loadTrips(): void {
    this.loading.set(true);
    this.tripService
      .getAll()
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.trips.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  loadDrivers(): void {
    this.driverService
      .getAll()
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.drivers.set(data);
        },
        error: (error) => {
          console.error('Ошибка загрузки водителей:', error);
        },
      });
  }

  loadCars(): void {
    this.carService
      .getAllCars()
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.cars.set(data);
        },
        error: (error) => {
          console.error('Ошибка загрузки автомобилей:', error);
        },
      });
  }

  openCreateModal(): void {
    this.newTripOrigin.set('');
    this.newTripDestination.set('');
    this.showCreateModal.set(true);
  }

  closeCreateModal(): void {
    this.showCreateModal.set(false);
  }

  openAssignModal(trip: TripResponseDto): void {
    this.selectedTrip.set(trip);
    this.assignDriverId.set(0);
    this.assignCarId.set(0);
    this.showAssignModal.set(true);
  }

  closeAssignModal(): void {
    this.showAssignModal.set(false);
    this.selectedTrip.set(null);
  }

  createTrip(): void {
    const origin = this.newTripOrigin();
    const destination = this.newTripDestination();

    if (!origin || !destination) {
      alert('Заполните все поля');
      return;
    }

    const trip: TripCreateDto = { origin, destination };

    this.tripService
      .create(trip)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Рейс создан!');
          this.closeCreateModal();
          this.loadTrips();
        },
        error: () => {
          alert('Не удалось создать рейс');
        },
      });
  }

  assignDriverAndCar(): void {
    const trip = this.selectedTrip();
    const driverId = this.assignDriverId();
    const carId = this.assignCarId();

    if (!trip || !driverId || !carId) {
      alert('Выберите водителя и автомобиль');
      return;
    }

    const assignData: TripAssignDto = { driverId, carId };

    this.tripService
      .assign(trip.id, assignData)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Водитель и автомобиль назначены!');
          this.closeAssignModal();
          this.loadTrips();
        },
        error: () => {
          alert('Не удалось назначить водителя и автомобиль');
        },
      });
  }

  startTrip(tripId: number): void {
    this.tripService
      .start(tripId)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Рейс начат!');
          this.loadTrips();
        },
        error: () => {
          alert('Не удалось начать рейс');
        },
      });
  }

  cancelTrip(tripId: number): void {
    if (!confirm('Вы уверены, что хотите отменить рейс?')) return;

    this.tripService
      .cancel(tripId)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Рейс отменён!');
          this.loadTrips();
        },
        error: () => {
          alert('Не удалось отменить рейс');
        },
      });
  }

  getStatusBadgeClass(status: TripStatus): string {
    switch (status) {
      case TripStatus.await:
        return 'badge-await';
      case TripStatus.in_progress:
        return 'badge-in-progress';
      case TripStatus.completed:
        return 'badge-completed';
      case TripStatus.cancelled:
        return 'badge-cancelled';
      default:
        return 'badge-default';
    }
  }

  getStatusText(status: TripStatus): string {
    switch (status) {
      case TripStatus.await:
        return 'Ожидает';
      case TripStatus.in_progress:
        return 'В пути';
      case TripStatus.completed:
        return 'Завершён';
      case TripStatus.cancelled:
        return 'Отменён';
      default:
        return status;
    }
  }

  getDriverName(driverId: number | null): string {
    if (!driverId) {
      return 'Не назначен';
    }
    const driver = this.drivers().find((d) => d.id === driverId);
    if (driver) {
      return driver.name;
    }
    return 'Не назначен';
  }

  getCarInfo(carId: number | null): string {
    if (!carId) {
      return 'Не назначен';
    }
    const car = this.cars().find((c) => c.id === carId);
    if (car) {
      return `${car.mark} ${car.model} (${car.stateNumber})`;
    }
    return 'Не назначен';
  }
}

