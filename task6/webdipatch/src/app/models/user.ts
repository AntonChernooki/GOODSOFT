import { Role } from './role';
export class User {
  private login: string;
  private password: string;

  private email: string;
  private surname: string;

  private name: string;
  private patronymic: string;

  private birthday: Date;
  private roles: Set<Role>;

  constructor(
    login: string,
    password: string,
    email: string,
    surname: string,
    name: string,
    patronymic: string,
    birthday: Date,
    roles: Set<Role> = new Set<Role>(),
  ) {
    this.login = login;
    this.password = password;
    this.email = email;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.birthday = birthday;
    this.roles = roles;
  }

  getBirthday(): Date {
    return this.birthday;
  }

  setBirthday(birthday: Date) {
    this.birthday = birthday;
  }

  getRoles(): Set<Role> {
    return new Set(this.roles);
  }
  setRoles(roles: Set<Role>) {
    this.roles = new Set(roles);
  }

  getPatronymic(): string {
    return this.patronymic;
  }
  setPatronymic(patronymic: string) {
    this.patronymic = patronymic;
  }

  getLogin(): string {
    return this.login;
  }
  setLogin(login: string) {
    this.login = login;
  }

  getPassword(): string {
    return this.password;
  }
  setPassword(password: string) {
    this.password = password;
  }

  getEmail(): string {
    return this.email;
  }
  setEmail(email: string) {
    this.email = email;
  }

  getSurname(): string {
    return this.surname;
  }
  setSurname(surname: string) {
    this.surname = surname;
  }

  getName(): string {
    return this.name;
  }
  setName(name: string) {
    this.name = name;
  }

  hasRole(role: Role): boolean {
    return this.roles.has(role);
  }

  isAdmin(): boolean {
    return this.hasRole(Role.ADMIN);
  }
}
