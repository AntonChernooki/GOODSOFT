import { Component, inject, input, output, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ChangeDetectionStrategy } from '@angular/core';
import { Subject } from 'rxjs';
import { RepairRequestCreateDto } from '../../../models/dto/request/repairRequest/RepairRequestCreateDto';
import { RepairRequestService } from '../../../services/repair-request/repair-requestService';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-repair-request-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './repair-request-form-component.html',
  styleUrls: ['./repair-request-form-component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RepairRequestFormComponent implements OnDestroy {
  private readonly repairRequestService = inject(RepairRequestService);
  private readonly formBuilder = inject(FormBuilder);
  private readonly destroySubject = new Subject<void>();

  readonly carId = input<number | null>(null);
  readonly driverId = input<number | null>(null);
  readonly close = output<void>();

  isSubmitting = signal<boolean>(false);
  form = this.formBuilder.group({
    description: ['', [Validators.required]],
  });

  ngOnDestroy(): void {
    this.destroySubject.next();
    this.destroySubject.complete();
  }

  submit(): void {
    const carId = this.carId();
    const driverId = this.driverId();

    if (this.form.invalid || !carId || !driverId) return;

    this.isSubmitting.set(true);

    const request: RepairRequestCreateDto = {
      carId,
      driverId,
      description: this.form.value.description!,
    };

    this.repairRequestService
      .create(request)
      .pipe(takeUntil(this.destroySubject))
      .subscribe({
        next: () => {
          alert('Заявка на ремонт отправлена!');
          this.close.emit();
        },
        error: (error) => {
          console.error('Ошибка при создании заявки:', error);
          const message = error?.error?.message || 'Не удалось отправить заявку';
          alert(message);
        },
        complete: () => {
          this.isSubmitting.set(false);
        },
      });
  }
}
