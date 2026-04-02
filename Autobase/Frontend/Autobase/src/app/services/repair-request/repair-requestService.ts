import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { RepairRequestCreateDto } from '../../models/dto/request/repairRequest/RepairRequestCreateDto';
import { RepairRequestUpdateDto } from '../../models/dto/request/repairRequest/RepairRequestUpdateDto';
import { RepairRequestResponseDto } from '../../models/dto/response/repairRequest/RepairRequestResponseDto';
import { RepairRequestStatus } from '../../models/enums/RepairRequestStatus';

@Injectable({ 
  providedIn: 'root'
 })
export class RepairRequestService {
  private apiUrl = `${environment.apiUrl}/repair-requests`;

  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<RepairRequestResponseDto[]> {
    return this.httpClient.get<RepairRequestResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<RepairRequestResponseDto> {
    return this.httpClient.get<RepairRequestResponseDto>(`${this.apiUrl}/${id}`);
  }

  create(repairRequestCreateDto: RepairRequestCreateDto): Observable<RepairRequestResponseDto> {
    return this.httpClient.post<RepairRequestResponseDto>(this.apiUrl, repairRequestCreateDto);
  }

  update(id: number, repairRequestUpdateDto: RepairRequestUpdateDto): Observable<RepairRequestResponseDto> {
    return this.httpClient.patch<RepairRequestResponseDto>(`${this.apiUrl}/${id}`, repairRequestUpdateDto);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateStatus(id: number, status: RepairRequestStatus): Observable<RepairRequestResponseDto> {
    return this.httpClient.patch<RepairRequestResponseDto>(`${this.apiUrl}/${id}/status`, { status });
  }

  getByStatus(status: RepairRequestStatus): Observable<RepairRequestResponseDto[]> {
    return this.httpClient.get<RepairRequestResponseDto[]>(`${this.apiUrl}/status/${status}`);
  }

  getByDriverId(driverId: number): Observable<RepairRequestResponseDto[]> {
    return this.httpClient.get<RepairRequestResponseDto[]>(`${this.apiUrl}/driver/${driverId}`);
  }

  getByCarId(carId: number): Observable<RepairRequestResponseDto[]> {
    return this.httpClient.get<RepairRequestResponseDto[]>(`${this.apiUrl}/car/${carId}`);
  }
}