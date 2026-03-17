import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Role } from '../models/role';
import { AuthService } from './auth-service';
@Injectable({
  providedIn: 'root',
})
export class UserService {
  private users: User[] = [
    new User(
      'admin',
      '1234',
      'admin@example.com',
      'Админов',
      'Админ',
      'Админович',
      new Date('1980-01-01'),
      new Set([Role.ADMIN, Role.USER]),
    ),
    new User(
      'user1',
      'pass1',
      'user1@example.com',
      'Иванов',
      'Иван',
      'Иванович',
      new Date('1990-05-15'),
      new Set([Role.USER]),
    ),
    new User(
      'user2',
      'pass2',
      'user2@example.com',
      'Петров',
      'Петр',
      'Петрович',
      new Date('1985-10-20'),
      new Set([Role.USER]),
    ),
  ];

  getAllUsers(): User[] {
    return [...this.users];
  }

  addUser(user: User): boolean {
    if (this.users.some(u => u.getLogin() === user.getLogin())) {
      return false;
    }
    this.users.push(user);
    return true;
  }

  updatePassword(login: string, newPassword: string): boolean {
    const user = this.users.find((u) => u.getLogin() === login);
    if (user) {
      user.setPassword(newPassword);
      return true;
    }
    return false;
  }
  getUserByLogin(login: string): User | undefined {
    return this.users.find((u) => u.getLogin() === login);
  }

  deleteUser(login: string): boolean {
    const index = this.users.findIndex((u) => u.getLogin() === login);
    if (index === -1) {
      return false;
    }
    this.users.splice(index, 1);
    return true;
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
  ): boolean {
    if (originalLogin !== login) {
      const existingUser = this.users.find(u => u.getLogin() === login);
      if (existingUser) {
        return false;
      }
    }

    const index = this.users.findIndex((u) => u.getLogin() === originalLogin);
    if (index === -1) {
      return false;
    }

    const updatedUser = new User(
      login,
      password,
      email,
      surname,
      name,
      patronymic,
      birthday,
      roles,
    );
    this.users[index] = updatedUser;

    return true;
  }
}
