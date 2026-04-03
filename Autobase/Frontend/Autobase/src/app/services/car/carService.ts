import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { CarResponseDto } from '../../models/dto/response/car/CarResponseDto';
import { CarCreateDto } from '../../models/dto/request/car/carCreateDto';
import { CarUpdateDto } from '../../models/dto/request/car/CarUpdateDto';
import { CarStatus } from '../../models/enums/CarStatus';

@Injectable({ providedIn: 'root' })
export class CarService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/cars`;

  getAllCars(): Observable<CarResponseDto[]> {
    return this.http.get<CarResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<CarResponseDto> {
    return this.http.get<CarResponseDto>(`${this.apiUrl}/${id}`);
  }

  create(carCreateDto: CarCreateDto): Observable<CarResponseDto> {
    return this.http.post<CarResponseDto>(this.apiUrl, carCreateDto);
  }

  update(id: number, updateDto: CarUpdateDto): Observable<CarResponseDto> {
    return this.http.patch<CarResponseDto>(`${this.apiUrl}/${id}`, updateDto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateStatus(id: number, status: CarStatus): Observable<CarResponseDto> {
    return this.http.patch<CarResponseDto>(`${this.apiUrl}/${id}/status`, { status });
  }

  getByYear(year: number): Observable<CarResponseDto[]> {
    return this.http.get<CarResponseDto[]>(`${this.apiUrl}/year/${year}`);
  }

  getByStatus(status: CarStatus): Observable<CarResponseDto[]> {
    return this.http.get<CarResponseDto[]>(`${this.apiUrl}/status/${status}`);
  }

  getByStateNumber(stateNumber: string): Observable<CarResponseDto[]> {
    return this.http.get<CarResponseDto[]>(`${this.apiUrl}/state-number/${stateNumber}`);
  }

  getByMark(mark: string): Observable<CarResponseDto[]> {
    return this.http.get<CarResponseDto[]>(`${this.apiUrl}/mark/${mark}`);
  }

  getByColor(color: string): Observable<CarResponseDto[]> {
    return this.http.get<CarResponseDto[]>(`${this.apiUrl}/color/${color}`);
  }
}
