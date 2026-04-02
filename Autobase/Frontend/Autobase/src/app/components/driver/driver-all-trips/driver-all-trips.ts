import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TripResponseDto } from '../../../models/dto/response/trip/TripResponseDto';
import { AuthService } from '../../../services/authService';
import { DriverService } from '../../../services/driver/driverService';
import { TripService } from '../../../services/trip/tripService';
import { Location } from '@angular/common';

@Component({
  selector: 'app-driver-all-trips',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './driver-all-trips.html',
  styleUrls: ['./driver-all-trips.css'],
})
export class DriverAllTripsComponent implements OnInit {
  trips: TripResponseDto[] = [];
  loading = false;

  constructor(
    private tripService: TripService,
    private driverService: DriverService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef, 
    private location:Location,
  ) {}

  ngOnInit(): void {
    this.loadAllTrips();
  }

  goBack(): void {
    this.location.back();
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'in_progress':
        return 'status-progress';
      case 'completed':
        return 'status-completed';
      case 'cancelled':
        return 'status-cancelled';
      default:
        return 'status-await';
    }
  }

  loadAllTrips(): void {
    this.loading = true;
    const user = this.authService.getCurrentUser();

    if (!user?.id) {
      this.loading = false;
      this.cdr.detectChanges();
      return;
    }

    this.driverService.getByUserId(user.id).subscribe({
      next: (driver) => {
        this.tripService.getByDriverId(driver.id).subscribe({
          next: (trips) => {
            this.trips = trips;
            this.loading = false;
            this.cdr.detectChanges(); 
          },
          error: (err) => {
            console.error('Ошибка загрузки рейсов:', err);
            this.loading = false;
            this.cdr.detectChanges();
          },
        });
      },
      error: (err) => {
        console.error('Ошибка загрузки водителя:', err);
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }

  getRoute(trip: TripResponseDto): string {
    return `${trip.origin} → ${trip.destination}`;
  }
}
