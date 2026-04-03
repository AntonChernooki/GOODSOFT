import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { DriverCreateDto } from '../../models/dto/request/driver/DriverCreateDto';
import { DriverUpdateDto } from '../../models/dto/request/driver/DriverUpdateDto';
import { DriverResponseDto } from '../../models/dto/response/driver/DriverResponseDto';
import { DriverStatus } from '../../models/enums/DriverStatus';

@Injectable({ providedIn: 'root' })
export class DriverService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/drivers`;

  getAll(): Observable<DriverResponseDto[]> {
    return this.http.get<DriverResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<DriverResponseDto> {
    return this.http.get<DriverResponseDto>(`${this.apiUrl}/${id}`);
  }

  create(driverCreateDto: DriverCreateDto): Observable<DriverResponseDto> {
    return this.http.post<DriverResponseDto>(this.apiUrl, driverCreateDto);
  }

  update(id: number, driverUpdateDto: DriverUpdateDto): Observable<DriverResponseDto> {
    return this.http.patch<DriverResponseDto>(`${this.apiUrl}/${id}`, driverUpdateDto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateStatus(id: number, status: DriverStatus): Observable<DriverResponseDto> {
    return this.http.patch<DriverResponseDto>(`${this.apiUrl}/${id}/status`, { status });
  }

  getByUserId(userId: number): Observable<DriverResponseDto> {
    return this.http.get<DriverResponseDto>(`${this.apiUrl}/user/${userId}`);
  }

  getActive(): Observable<DriverResponseDto[]> {
    return this.http.get<DriverResponseDto[]>(`${this.apiUrl}/active`);
  }
}
