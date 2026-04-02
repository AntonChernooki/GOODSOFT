import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environments';
import { Observable } from 'rxjs';
import { CarResponseDto } from '../../models/dto/response/car/CarResponseDto';
import { HttpClient } from '@angular/common/http';
import { CarCreateDto } from '../../models/dto/request/car/carCreateDto';
import { CarUpdateDto } from '../../models/dto/request/car/CarUpdateDto';
import { CarStatus } from '../../models/enums/CarStatus';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  private apiUrl = `${environment.apiUrl}/cars`;

  constructor(private httpClient: HttpClient) {}

  getAllCars(): Observable<CarResponseDto[]> {
    return this.httpClient.get<CarResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<CarResponseDto> {
    return this.httpClient.get<CarResponseDto>(`${this.apiUrl}/${id}`);
  }

  create(carCreateDto: CarCreateDto): Observable<CarResponseDto> {
    return this.httpClient.post<CarResponseDto>(this.apiUrl, carCreateDto);
  }

  update(id: number, dto: CarUpdateDto): Observable<CarResponseDto> {
    return this.httpClient.patch<CarResponseDto>(`${this.apiUrl}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateStatus(id: number, status: CarStatus): Observable<CarResponseDto> {
    return this.httpClient.patch<CarResponseDto>(`${this.apiUrl}/${id}/status`, { status });
  }

  getByYear(year: number): Observable<CarResponseDto[]> {
    return this.httpClient.get<CarResponseDto[]>(`${this.apiUrl}/year/${year}`);
  }

  getByStatus(status: CarStatus): Observable<CarResponseDto[]> {
    return this.httpClient.get<CarResponseDto[]>(`${this.apiUrl}/status/${status}`);
  }

  getByStateNumber(stateNumber: string): Observable<CarResponseDto[]> {
    return this.httpClient.get<CarResponseDto[]>(`${this.apiUrl}/state-number/${stateNumber}`);
  }

  getByMark(mark: string): Observable<CarResponseDto[]> {
    return this.httpClient.get<CarResponseDto[]>(`${this.apiUrl}/mark/${mark}`);
  }

  getByColor(color: string): Observable<CarResponseDto[]> {
    return this.httpClient.get<CarResponseDto[]>(`${this.apiUrl}/color/${color}`);
  }
}
