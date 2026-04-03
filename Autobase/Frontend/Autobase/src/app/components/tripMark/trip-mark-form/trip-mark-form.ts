import { Component, inject, input, output, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { TripMarkRequestDto } from '../../../models/dto/request/tripMark/TripMarkRequestDto';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { TripMarkService } from '../../../services/trip-mark/trip-markService';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-trip-mark-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './trip-mark-form.html',
  styleUrls: ['./trip-mark-form.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TripMarkFormComponent implements OnDestroy {
  private readonly tripMarkService = inject(TripMarkService);
  private readonly destroySubject = new Subject<void>();

  readonly trip = input<TripResponseDto | null>(null);
  readonly close = output<void>();
  readonly openRepair = output<void>();

  fuelConsumed = 0;
  conditionNotes = '';

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  submit(): void {
    const trip = this.trip();
    if (!trip) {
      return;
    }

    if (this.fuelConsumed < 0 || this.fuelConsumed > 99999999.99) {
      alert('Потрачено топлива должно быть от 0 до 99 999 999.99 литров');
      return;
    }

    const request: TripMarkRequestDto = {
      tripId: trip.id,
      fuelConsumed: Number(this.fuelConsumed),
      conditionNotes: this.conditionNotes,
    };

    this.tripMarkService
      .create(request)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Оценка сохранена!');
          this.close.emit();
        },
        error: (error) => {
          console.error('Ошибка при сохранении оценки:', error);
          alert('Не удалось сохранить оценку');
        },
      });
  }
}
