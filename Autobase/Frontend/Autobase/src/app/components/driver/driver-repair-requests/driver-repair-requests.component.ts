import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { RepairRequestService } from '../../../services/repair-request/repair-requestService';
import { DriverService } from '../../../services/driver/driverService';
import { CarService } from '../../../services/car/carService';
import { AuthService } from '../../../services/authService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject, firstValueFrom } from 'rxjs';
import { RepairRequestResponseDto } from '../../../models/dto/response/repairRequest/RepairRequestResponseDto';
import { DriverResponseDto } from '../../../models/dto/response/driver/DriverResponseDto';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { RepairRequestStatus } from '../../../models/enums/RepairRequestStatus';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-driver-repair-requests',
  imports: [CommonModule],
  templateUrl: './driver-repair-requests.component.html',
  styleUrls: ['./driver-repair-requests.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DriverRepairRequestsComponent implements OnDestroy {
  private readonly repairRequestService = inject(RepairRequestService);
  private readonly driverService = inject(DriverService);
  private readonly carService = inject(CarService);
  private readonly authService = inject(AuthService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  requests = signal<(RepairRequestResponseDto & { carInfo?: CarResponseDto })[]>([]);
  loading = signal<boolean>(true);
  driverId = signal<number | null>(null);
  readonly RepairRequestStatus = RepairRequestStatus;

  constructor() {
    this.loadDriverAndRequests();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  private loadDriverAndRequests(): void {
    const user = this.authService.getCurrentUser();
    if (!user?.id) {
      this.loading.set(false);
      return;
    }

    this.driverService
      .getByUserId(user.id)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (driver: DriverResponseDto) => {
          this.driverId.set(driver.id);
          this.loadRequests(driver.id);
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  private async loadRequests(driverId: number): Promise<void> {
    this.repairRequestService
      .getByDriverId(driverId)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: async (data) => {
          if (data.length === 0) {
            this.requests.set([]);
            this.loading.set(false);
            return;
          }

          const carIds = [...new Set(data.map((r) => r.carId).filter(Boolean))] as number[];

          if (carIds.length === 0) {
            const requestsWithoutCars = data.map((req) => ({ ...req, carInfo: undefined }));
            this.requests.set(requestsWithoutCars);
            this.loading.set(false);
            return;
          }

          const carObservables = carIds.map((id) => this.carService.getById(id));

          try {
            const cars = await Promise.all(carObservables.map((observable) => firstValueFrom(observable)));
            const carMap = new Map<number, CarResponseDto>();
            cars.forEach((car, index) => {
              if (car) {
                carMap.set(carIds[index], car);
              }
            });

            const enrichedRequests = data.map((req) => {
              let carInfo: CarResponseDto | undefined;
              if (req.carId) {
                carInfo = carMap.get(req.carId);
              }
              return { ...req, carInfo };
            });

            this.requests.set(enrichedRequests);
            this.loading.set(false);
          } catch {
            const requestsWithoutCars = data.map((req) => ({ ...req, carInfo: undefined }));
            this.requests.set(requestsWithoutCars);
            this.loading.set(false);
          }
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  getCarInfo(request: RepairRequestResponseDto & { carInfo?: CarResponseDto }): string {
    if (request.carInfo) {
      return `${request.carInfo.mark} ${request.carInfo.model} (${request.carInfo.stateNumber})`;
    }
    return 'Неизвестно';
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
}

