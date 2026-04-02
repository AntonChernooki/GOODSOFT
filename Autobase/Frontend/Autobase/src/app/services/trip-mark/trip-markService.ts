import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { TripMarkRequestDto } from '../../models/dto/request/tripMark/TripMarkRequestDto';
import { TripMarkResponseDto } from '../../models/dto/response/tripMark/TripMarkResponseDto';


@Injectable({ providedIn: 'root' })
export class TripMarkService {
  private apiUrl = `${environment.apiUrl}/trip-marks`;

  constructor(private httpClient: HttpClient) {}

  create(tripMarkRequestDto: TripMarkRequestDto): Observable<TripMarkResponseDto> {
    return this.httpClient.post<TripMarkResponseDto>(this.apiUrl, tripMarkRequestDto);
  }

  getById(id: number): Observable<TripMarkResponseDto> {
    return this.httpClient.get<TripMarkResponseDto>(`${this.apiUrl}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }

  getByTripId(tripId: number): Observable<TripMarkResponseDto[]> {
    return this.httpClient.get<TripMarkResponseDto[]>(`${this.apiUrl}/trip/${tripId}`);
  }

  deleteByTripId(tripId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/trip/${tripId}`);
  }
}