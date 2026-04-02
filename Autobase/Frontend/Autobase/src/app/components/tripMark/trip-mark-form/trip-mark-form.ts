import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TripMarkRequestDto } from '../../../models/dto/request/tripMark/TripMarkRequestDto';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { TripMarkService } from '../../../services/trip-mark/trip-markService';






@Component({
  selector: 'app-trip-mark-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './trip-mark-form.html',
  styleUrl: './trip-mark-form.css',
})
export class TripMarkFormComponent {
  @Input() trip: TripResponseDto | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() openRepair = new EventEmitter<void>();

  fuelConsumed = 0;
  conditionNotes = '';

  constructor(private tripMarkService: TripMarkService) {}

  submit(): void {
    if (!this.trip) return;

    const request: TripMarkRequestDto = {
      tripId: this.trip.id,
      fuelConsumed: this.fuelConsumed,
      conditionNotes: this.conditionNotes,
    };

    this.tripMarkService.create(request).subscribe({
      next: () => {
        alert('Оценка сохранена!');
        this.close.emit();
      },
      error: (err) => {
        console.error('Ошибка при сохранении оценки:', err);
        alert('Не удалось сохранить оценку');
      },
    });
  }
}