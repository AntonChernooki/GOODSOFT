import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CarService } from '../../../services/car/carService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { CarStatus } from '../../../models/enums/CarStatus';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-dispatcher-cars',
  imports: [CommonModule, RouterLink],
  templateUrl: './dispatcher-cars.component.html',
  styleUrls: ['./dispatcher-cars.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DispatcherCarsComponent implements OnDestroy {
  private carService = inject(CarService);
  private location = inject(Location);
  private destroySubject = new Subject<void>();

  cars = signal<CarResponseDto[]>([]);
  loading = signal<boolean>(false);
  CarStatus = CarStatus;

  constructor() {
    this.loadCars();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  loadCars(): void {
    this.loading.set(true);
    this.carService
      .getAllCars()
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.cars.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  getStatusBadgeClass(status: CarStatus): string {
    switch (status) {
      case CarStatus.available:
        return 'badge-available';
      case CarStatus.in_use:
        return 'badge-in-use';
      case CarStatus.repair:
        return 'badge-repair';
      default:
        return 'badge-default';
    }
  }

  getStatusText(status: CarStatus): string {
    switch (status) {
      case CarStatus.available:
        return 'Свободен';
      case CarStatus.in_use:
        return 'В рейсе';
      case CarStatus.repair:
        return 'В ремонте';
      default:
        return status;
    }
  }

  getMileageUnit(mileage: number): string {
    return `${mileage.toLocaleString()} км`;
  }
}

