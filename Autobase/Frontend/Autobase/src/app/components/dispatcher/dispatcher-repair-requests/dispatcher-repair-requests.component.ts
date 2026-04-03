import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RepairRequestService } from '../../../services/repair-request/repair-requestService';
import { CarService } from '../../../services/car/carService';
import { DriverService } from '../../../services/driver/driverService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { RepairRequestResponseDto } from '../../../models/dto/response/repairRequest/RepairRequestResponseDto';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { DriverResponseDto } from '../../../models/dto/response/driver/DriverResponseDto';
import { RepairRequestStatus } from '../../../models/enums/RepairRequestStatus';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-dispatcher-repair-requests',
  imports: [CommonModule],
  templateUrl: './dispatcher-repair-requests.component.html',
  styleUrls: ['./dispatcher-repair-requests.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DispatcherRepairRequestsComponent implements OnDestroy {
  private readonly repairRequestService = inject(RepairRequestService);
  private readonly carService = inject(CarService);
  private readonly driverService = inject(DriverService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  requests = signal<RepairRequestResponseDto[]>([]);
  cars = signal<CarResponseDto[]>([]);
  drivers = signal<DriverResponseDto[]>([]);
  loading = signal<boolean>(false);
  readonly RepairRequestStatus = RepairRequestStatus;

  constructor() {
    this.loadRequests();
    this.loadCars();
    this.loadDrivers();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  loadRequests(): void {
    this.loading.set(true);
    this.repairRequestService
      .getAll()
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.requests.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
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

  updateStatus(id: number, status: RepairRequestStatus): void {
    const statusNames: Record<RepairRequestStatus, string> = {
      [RepairRequestStatus.submitted]: 'принята',
      [RepairRequestStatus.in_progress]: 'в работу',
      [RepairRequestStatus.complete]: 'завершена',
      [RepairRequestStatus.rejected]: 'отклонена',
    };

    if (!confirm(`Изменить статус заявки на "${statusNames[status]}"?`)) {
      return;
    }

    this.repairRequestService
      .updateStatus(id, status)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Статус заявки обновлён!');
          this.loadRequests();
        },
        error: () => {
          alert('Не удалось обновить статус');
        },
      });
  }

  getStatusBadgeClass(status: RepairRequestStatus): string {
    switch (status) {
      case RepairRequestStatus.submitted:
        return 'badge-submitted';
      case RepairRequestStatus.in_progress:
        return 'badge-in-progress';
      case RepairRequestStatus.complete:
        return 'badge-complete';
      case RepairRequestStatus.rejected:
        return 'badge-rejected';
      default:
        return 'badge-default';
    }
  }

  getStatusText(status: RepairRequestStatus): string {
    switch (status) {
      case RepairRequestStatus.submitted:
        return 'Подана';
      case RepairRequestStatus.in_progress:
        return 'В работе';
      case RepairRequestStatus.complete:
        return 'Завершена';
      case RepairRequestStatus.rejected:
        return 'Отклонена';
      default:
        return status;
    }
  }

  getDriverName(driverId: number): string {
    const driver = this.drivers().find((d) => d.id === driverId);
    if (driver) {
      return driver.name;
    }
    return 'Неизвестно';
  }

  getCarInfo(carId: number): string {
    const car = this.cars().find((c) => c.id === carId);
    if (car) {
      return `${car.mark} ${car.model} (${car.stateNumber})`;
    }
    return 'Неизвестно';
  }
}

