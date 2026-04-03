import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { TripMarkRequestDto } from '../../models/dto/request/tripMark/TripMarkRequestDto';
import { TripMarkResponseDto } from '../../models/dto/response/tripMark/TripMarkResponseDto';

@Injectable({ providedIn: 'root' })
export class TripMarkService {
  private  http = inject(HttpClient);
  private  apiUrl = `${environment.apiUrl}/trip-marks`;

  create(tripMarkRequestDto: TripMarkRequestDto): Observable<TripMarkResponseDto> {
    return this.http.post<TripMarkResponseDto>(this.apiUrl, tripMarkRequestDto);
  }

  getById(id: number): Observable<TripMarkResponseDto> {
    return this.http.get<TripMarkResponseDto>(`${this.apiUrl}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getByTripId(tripId: number): Observable<TripMarkResponseDto[]> {
    return this.http.get<TripMarkResponseDto[]>(`${this.apiUrl}/trip/${tripId}`);
  }

  deleteByTripId(tripId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/trip/${tripId}`);
  }
}
