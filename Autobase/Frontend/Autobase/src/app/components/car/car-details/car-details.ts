import { Component, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Location } from '@angular/common';
import { CarService } from '../../../services/car/carService';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-car-details',
  imports: [CommonModule, RouterModule],
  templateUrl: './car-details.html',
  styleUrls: ['./car-details.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CarDetailsComponent implements OnDestroy {
  private readonly route = inject(ActivatedRoute);
  private readonly carService = inject(CarService);
  private readonly location = inject(Location);
  private readonly destroySubject = new Subject<void>();

  car = signal<CarResponseDto | null>(null);
  loading = signal<boolean>(false);
  error = signal<boolean>(false);

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.loadCar(+idParam);
    }
  }

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  loadCar(id: number): void {
    this.loading.set(true);
    this.error.set(false);

    this.carService
      .getById(id)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: (data) => {
          this.car.set(data);
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

