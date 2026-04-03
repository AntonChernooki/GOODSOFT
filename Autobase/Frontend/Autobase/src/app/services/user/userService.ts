import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { UserSetEnabledDto } from '../../models/dto/request/user/UserSetEnabledDto';
import { UserResponseDto } from '../../models/dto/response/user/UserResponseDto';
import { UserUpdateDto } from '../../models/dto/request/user/UserUpdateDto';

@Injectable({ providedIn: 'root' })
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/users`;

  getAll(): Observable<UserResponseDto[]> {
    return this.http.get<UserResponseDto[]>(this.apiUrl);
  }

  getById(id: number): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.apiUrl}/id/${id}`);
  }

  getByLogin(login: string): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.apiUrl}/login/${login}`);
  }

  update(id: number, userUpdateDto: UserUpdateDto): Observable<UserResponseDto> {
    return this.http.patch<UserResponseDto>(`${this.apiUrl}/${id}`, userUpdateDto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  setEnabled(id: number, setEnabledDto: UserSetEnabledDto): Observable<UserResponseDto> {
    return this.http.put<UserResponseDto>(`${this.apiUrl}/${id}/enabled`, setEnabledDto);
  }
}
