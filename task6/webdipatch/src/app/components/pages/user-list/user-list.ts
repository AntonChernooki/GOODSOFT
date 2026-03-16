import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../service/user-service';
import { AuthService } from '../../../service/auth';
import { User } from '../../../models/user';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-user-list',
  imports: [CommonModule, RouterLink, TranslateModule],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList {
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private translate: TranslateService,
  ) {}

  userList: User[] = [];

  ngOnInit(): void {
    this.userList = this.userService.getAllUsers();
  }

  onDeleteUser(login: string): void {
    const confirmMessage = this.translate.instant('CONFIRM_DELETE_USER', { login });
    if (confirm(confirmMessage)) {
      if (this.authService.getCurrentUser()?.getLogin() === login) {
        alert(this.translate.instant('CANNOT_DELETE_SELF'));
      } else {
        const success = this.userService.deleteUser(login);
        if (success) {
          this.userList = this.userService.getAllUsers();
        } else {
          alert(this.translate.instant('DELETE_USER_ERROR'));
        }
      }
    }
  }
}
