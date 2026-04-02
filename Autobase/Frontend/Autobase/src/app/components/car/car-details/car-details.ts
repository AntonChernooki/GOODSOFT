import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Location } from '@angular/common';
import { CarResponseDto } from '../../../models/dto/response/car/CarResponseDto';
import { CarService } from '../../../services/car/carService';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-car-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './car-details.html',
  styleUrls: ['./car-details.css'],
})
export class CarDetailsComponent implements OnInit {
  car: CarResponseDto | null = null;
  loading = false;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private location: Location,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.loadCar(+idParam);
    }
  }

  loadCar(id: number): void {
    this.loading = true;
    this.error = false;
    this.cdr.detectChanges();

    this.carService.getById(id).pipe(
      catchError((err) => {
        console.error('Ошибка загрузки автомобиля:', err);
        this.error = true;
        this.loading = false;
        this.cdr.detectChanges();
        return of(null);
      })
    ).subscribe({
      next: (data) => {
        if (data) {
          this.car = data;
        } else {
          this.error = true;
        }
        this.loading = false;
        this.cdr.detectChanges();
      },
      // error уже обработан в catchError
    });
  }

  goBack(): void {
    this.location.back();
  }
}