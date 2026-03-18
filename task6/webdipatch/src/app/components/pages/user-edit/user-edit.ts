import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from '../../../service/user-service';
import { AuthService } from '../../../service/auth-service';
import { User } from '../../../models/user';
import { Role } from '../../../models/role';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-user-edit',
  imports: [CommonModule, FormsModule, RouterLink, TranslateModule],
  templateUrl: './user-edit.html',
  styleUrl: './user-edit.css',
})
export class UserEditComponent {
  login: string = '';
  password: string = '';
  email: string = '';
  surname: string = '';
  name: string = '';
  patronymic: string = '';
  birthday: string = '';
  roles: Set<Role> = new Set();

  allRoles: Role[] = [Role.ADMIN, Role.USER];
  originalLogin: string | null = null;
  errorMessage = signal<string>('');
  isEditMode: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
     private changeDetector: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const loginParam = this.route.snapshot.paramMap.get('login');
    if (loginParam && loginParam !== 'new') {
      this.isEditMode = true;
      this.originalLogin = loginParam;
      const user = this.userService.getUserByLogin(loginParam).subscribe({
        next: (user) => {
          this.login = user.getLogin();
          this.password = '';
          this.email = user.getEmail();
          this.surname = user.getSurname();
          this.name = user.getName();
          this.patronymic = user.getPatronymic();
          this.birthday = user.getBirthday().toISOString().split('T')[0];
          this.roles = new Set(user.getRoles());
          this.changeDetector.detectChanges();
        },
        error: (err) => {
          this.router.navigate(['/userlist']);
          console.error('Ошибка загрузки пользователя', err);
        },
      });
    } else {
      this.isEditMode = false;
      this.roles.add(Role.USER);
    }

    
  }

  toggleRole(role: Role, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      this.roles.add(role);
    } else {
      this.roles.delete(role);
    }
  }

  isRoleChecked(role: Role): boolean {
    return this.roles.has(role);
  }

  onSubmit(form: NgForm): void {
    this.errorMessage.set('');

    if (form.invalid) {
      this.errorMessage.set('FILL_ALL_FIELDS');
      return;
    }

    const birthdayDate = this.birthday ? new Date(this.birthday + 'T00:00:00') : new Date();

    if (this.isEditMode) {
      this.userService
        .updateUser(
          this.originalLogin!,
          this.login,
          this.password,
          this.email,
          this.surname,
          this.name,
          this.patronymic,
          birthdayDate,
          this.roles,
        )
        .subscribe((success) => {
          if (success) {
            this.router.navigate(['/userlist']);
          } else {
            this.errorMessage.set('SAVE_ERROR');
          }
        });
    } else {
      const newUser = new User(
        this.login,
        this.password,
        this.email,
        this.surname,
        this.name,
        this.patronymic,
        birthdayDate,
        this.roles,
      );
      this.userService.addUser(newUser).subscribe((success) => {
        if (success) {
          this.router.navigate(['/userlist']);
        } else {
          this.errorMessage.set('USER_ALREADY_EXISTS');
        }
      });
    }
  }
}
