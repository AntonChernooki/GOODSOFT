import { User } from '../models/user';
import { Role } from '../models/role';
import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root',
})
export class UserMapper {
  static toUser(data: any): User {
    const rolesSet = new Set<Role>(data.roles);
    return new User(
      data.login,
      '',
      data.email,
      data.surname,
      data.name,
      data.patronymic,
      new Date(data.birthday),
      rolesSet,
    );
  }
  static toDto(user: User, includePassword: boolean): any {
    const dto: any = {
      login: user.getLogin(),
      email: user.getEmail(),
      surname: user.getSurname(),
      name: user.getName(),
      patronymic: user.getPatronymic(),
      birthday: this.formatDate(user.getBirthday()),
      roles: Array.from(user.getRoles()),
    };
    if (includePassword) {
      dto.password = user.getPassword();
    }
    return dto;
  }
  private static formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  static toDtoForUpdate(
    login: string,
    password: string | undefined,
    email: string,
    surname: string,
    name: string,
    patronymic: string,
    birthday: Date,
    roles: Set<Role>,
  ): any {
    const dto: any = {
      login,
      email,
      surname,
      name,
      patronymic,
      birthday: this.formatDate(birthday),
      roles: Array.from(roles),
    };
    if (password) {
      dto.password = password;
    }
    return dto;
  }
}
