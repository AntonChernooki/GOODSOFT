import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RepairRequestCreateDto } from '../../../models/dto/request/repairRequest/RepairRequestCreateDto';
import { RepairRequestService } from '../../../services/repair-request/repair-requestService';

@Component({
  selector: 'app-repair-request-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './repair-request-form-component.html',
  styleUrl: './repair-request-form-component.css',
    
      
})
export class RepairRequestFormComponent {
  @Input() carId: number | null = null;
  @Input() driverId: number | null = null;
  @Output() close = new EventEmitter<void>();

  description = '';

  constructor(private repairRequestService: RepairRequestService) {}

  submit(): void {
    if (!this.carId || !this.driverId) {
      alert('Не хватает данных для создания заявки');
      return;
    }

    const request: RepairRequestCreateDto = {
      carId: this.carId,
      driverId: this.driverId,
      description: this.description,
    };

    this.repairRequestService.create(request).subscribe({
      next: () => {
        alert('Заявка на ремонт отправлена!');
        this.close.emit();
      },
      error: (err) => {
        console.error('Ошибка при создании заявки:', err);
        alert('Не удалось отправить заявку');
      },
    });
  }
}