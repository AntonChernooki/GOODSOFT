import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { DriverCreateDto } from '../../models/dto/request/driver/DriverCreateDto';
import { DriverUpdateDto } from '../../models/dto/request/driver/DriverUpdateDto';
import { DriverResponseDto } from '../../models/dto/response/driver/DriverResponseDto';
import { DriverStatus } from '../../models/enums/DriverStatus';

@Injectable({ 
  providedIn: 'root' 
})
export class DriverService {
  private apiUrl = `${environment.apiUrl}/drivers`;

  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<DriverResponseDto[]> {
    return this.httpClient.get<DriverResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<DriverResponseDto> {
    return this.httpClient.get<DriverResponseDto>(`${this.apiUrl}/${id}`);
  }

  create(driverCreateDto: DriverCreateDto): Observable<DriverResponseDto> {
    return this.httpClient.post<DriverResponseDto>(this.apiUrl, driverCreateDto);
  }

  update(id: number, driverUpdateDto: DriverUpdateDto): Observable<DriverResponseDto> {
    return this.httpClient.patch<DriverResponseDto>(`${this.apiUrl}/${id}`, driverUpdateDto);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateStatus(id: number, status: DriverStatus): Observable<DriverResponseDto> {
    return this.httpClient.patch<DriverResponseDto>(`${this.apiUrl}/${id}/status`, { status });
  }

  getByUserId(userId: number): Observable<DriverResponseDto> {
    return this.httpClient.get<DriverResponseDto>(`${this.apiUrl}/user/${userId}`);
  }

  getActive(): Observable<DriverResponseDto[]> {
    return this.httpClient.get<DriverResponseDto[]>(`${this.apiUrl}/active`);
  }
}
