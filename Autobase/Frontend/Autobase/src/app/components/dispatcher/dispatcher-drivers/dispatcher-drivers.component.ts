import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DriverService } from '../../../services/driver/driverService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { DriverResponseDto } from '../../../models/dto/response/driver/DriverResponseDto';
import { DriverStatus } from '../../../models/enums/DriverStatus';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-dispatcher-drivers',
  imports: [CommonModule],
  templateUrl: './dispatcher-drivers.component.html',
  styleUrls: ['./dispatcher-drivers.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DispatcherDriversComponent implements OnDestroy {
  private readonly driverService = inject(DriverService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  drivers = signal<DriverResponseDto[]>([]);
  loading = signal<boolean>(false);
  readonly DriverStatus = DriverStatus;

  constructor() {
    this.loadDrivers();
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  goBack(): void {
    this.location.back();
  }

  loadDrivers(): void {
    this.loading.set(true);
    this.driverService
      .getAll()
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.drivers.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  toggleDriverStatus(driver: DriverResponseDto): void {
    const newStatus =
      driver.status === DriverStatus.active
        ? DriverStatus.inactive
        : DriverStatus.active;

    let actionText: string;
    if (newStatus === DriverStatus.active) {
      actionText = 'активировать';
    } else {
      actionText = 'отстранить';
    }

    if (!confirm(`Вы уверены, что хотите ${actionText} водителя ${driver.name}?`)) {
      return;
    }

    this.driverService
      .updateStatus(driver.id, newStatus)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          let statusText: string;
          if (newStatus === DriverStatus.active) {
            statusText = 'активен';
          } else {
            statusText = 'не активен';
          }
          alert(`Статус водителя изменён на ${statusText}`);
          this.loadDrivers();
        },
        error: () => {
          alert('Не удалось изменить статус водителя');
        },
      });
  }

  getStatusBadgeClass(status: DriverStatus): string {
    if (status === DriverStatus.active) {
      return 'badge-active';
    }
    return 'badge-inactive';
  }

  getStatusText(status: DriverStatus): string {
    if (status === DriverStatus.active) {
      return 'Активен';
    }
    return 'Отстранён';
  }
}

