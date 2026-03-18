import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../service/user-service';
import { AuthService } from '../../../service/auth-service';
import { User } from '../../../models/user';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';

import { NotificationService } from '../../../service/notification-service';
@Component({
  selector: 'app-user-list',
  imports: [CommonModule, RouterLink, TranslateModule, ConfirmDialogModule, ToastModule],
  providers: [ConfirmationService],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserListComponent {
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private translate: TranslateService,
    private confirmationService: ConfirmationService,
    private notificationService: NotificationService,
     private changeDetector: ChangeDetectorRef
  ) {}

  userList: User[] = [];

  ngOnInit(): void {
    this.loadUsers();
    
  }
  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        
        console.log('Загружены пользователи:', users);
        this.userList = users;
        this.changeDetector.detectChanges();
      },

      error: (err) =>{
        console.log(err),
        this.notificationService.error(
          this.translate.instant('ERROR'),
          this.translate.instant('LOAD_USERS_ERROR'),
        )},
    });
  }
   getRolesString(user: User): string {
    console.log('getRolesString for', user.getLogin(), user.getRoles());
    return Array.from(user.getRoles()).join(', ');
  }

  onDeleteUser(login: string): void {
    const confirmMessage = this.translate.instant('CONFIRM_DELETE_USER', { login });

    this.confirmationService.confirm({
      header: this.translate.instant('CONFIRM_DELETE_TITLE'),
      message: confirmMessage,
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.authService.getCurrentUser()?.getLogin() === login) {
          this.notificationService.warn(
            this.translate.instant('ACTION_FORBIDDEN'),
            this.translate.instant('CANNOT_DELETE_SELF'),
          );
        } else {
          const success = this.userService.deleteUser(login).subscribe(success=>{
if (success) {
            this.loadUsers();
            this.notificationService.success(
              this.translate.instant('SUCCESS'),
              this.translate.instant('USER_DELETED', { login }),
            );
          } else {
            this.notificationService.error(
              this.translate.instant('ERROR'),
              this.translate.instant('DELETE_USER_ERROR'),
            );
          }
          });
          
        }
      },
      reject: () => {},
    });
  }
}
