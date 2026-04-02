import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { TripAssignDto } from '../../models/dto/request/trip/TripAssignDto';
import { TripCreateDto } from '../../models/dto/request/trip/TripCreateDto';
import { TripUpdateDto } from '../../models/dto/request/trip/TripUpdateDto';
import { TripResponseDto } from '../../models/dto/response/trip/TripResponseDto';
import { TripStatus } from '../../models/enums/TripStatus';

@Injectable({ providedIn: 'root' })
export class TripService {
  private apiUrl = `${environment.apiUrl}/trips`;

  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<TripResponseDto[]> {
    return this.httpClient.get<TripResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<TripResponseDto> {
    return this.httpClient.get<TripResponseDto>(`${this.apiUrl}/${id}`);
  }

  create(tripCreateDto: TripCreateDto): Observable<TripResponseDto> {
    return this.httpClient.post<TripResponseDto>(this.apiUrl, tripCreateDto);
  }

  update(id: number, tripUpdateDto: TripUpdateDto): Observable<TripResponseDto> {
    return this.httpClient.patch<TripResponseDto>(`${this.apiUrl}/${id}`, tripUpdateDto);
  }

  assign(tripId: number, assignDto: TripAssignDto): Observable<TripResponseDto> {
    return this.httpClient.put<TripResponseDto>(`${this.apiUrl}/${tripId}/assign`, assignDto);
  }

  start(tripId: number): Observable<TripResponseDto> {
    return this.httpClient.post<TripResponseDto>(`${this.apiUrl}/${tripId}/start`, {});
  }

  complete(tripId: number): Observable<TripResponseDto> {
    return this.httpClient.post<TripResponseDto>(`${this.apiUrl}/${tripId}/complete`, {});
  }

  cancel(tripId: number): Observable<TripResponseDto> {
    return this.httpClient.post<TripResponseDto>(`${this.apiUrl}/${tripId}/cancel`, {});
  }

  getByStatus(status: TripStatus): Observable<TripResponseDto[]> {
    return this.httpClient.get<TripResponseDto[]>(`${this.apiUrl}/status/${status}`);
  }

  getByDriverId(driverId: number): Observable<TripResponseDto[]> {
    return this.httpClient.get<TripResponseDto[]>(`${this.apiUrl}/driver/${driverId}`);
  }

  getByCarId(carId: number): Observable<TripResponseDto[]> {
    return this.httpClient.get<TripResponseDto[]>(`${this.apiUrl}/car/${carId}`);
  }
}
