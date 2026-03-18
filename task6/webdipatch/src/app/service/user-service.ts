import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Role } from '../models/role';
import { HttpClient } from '@angular/common/http';
import { UserMapper } from '../mappers/UserMapper';
import { catchError, map, Observable, of } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8081/webdipatch/api/users';
  constructor(private httpClient: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    return this.httpClient
      .get<any[]>(this.apiUrl)
      .pipe(map((users) => users.map((user) => UserMapper.toUser(user))));
  }

  addUser(user: User): Observable<boolean> {
    const userDto = UserMapper.toDto(user, true);
    return this.httpClient.post(this.apiUrl, userDto, { observe: 'response' }).pipe(
      map((response) => response.status === 201),
      catchError((error) => {
        console.error(error, 'ошибка добавления пользователя');
        return of(false);
      }),
    );
  }

  getUserByLogin(login: string): Observable<User> {
    return this.httpClient
      .get<any>(this.apiUrl + `/${login}`)
      .pipe(map((user) => UserMapper.toUser(user)));
  }

  deleteUser(login: string): Observable<boolean> {
    return this.httpClient.delete(this.apiUrl + `/${login}`, { observe: 'response' }).pipe(
      map((response) => response.status === 200),
      catchError((error) => {
        console.error('Ошибка удаления пользователя', error);
        return of(false);
      }),
    );
  }

  updateUser(
    originalLogin: string,
    login: string,
    password: string,
    email: string,
    surname: string,
    name: string,
    patronymic: string,
    birthday: Date,
    roles: Set<Role>,
  ): Observable<boolean> {
    const dto = UserMapper.toDtoForUpdate(
      login,
      password,
      email,
      surname,
      name,
      patronymic,
      birthday,
      roles,
    );
    return this.httpClient
      .put(`${this.apiUrl}/${originalLogin}`, dto, { observe: 'response' })
      .pipe(
        map((response) => response.status === 200),
        catchError((error) => {
          console.error('Ошибка обновления пользователя', error);
          return of(false);
        }),
      );
  }
}
