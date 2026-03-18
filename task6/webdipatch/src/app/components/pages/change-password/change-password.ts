import { Component, signal } from '@angular/core';
import { AuthService } from '../../../service/auth-service';
import { RouterLink } from '@angular/router';
import { User } from '../../../models/user';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-change-password',
  imports: [RouterLink, FormsModule, TranslateModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css',
})
export class ChangePasswordComponent {
  oldPassword = '';
  newPassword = '';

  successMessage = signal<string>('');
  errorMessage = signal<string>('');

  currentUser: User | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
   
  }

  onChangePassword(form: NgForm): void {
    this.successMessage.set('');
    this.errorMessage.set('');

    if (form.invalid) {
      this.errorMessage.set('FILL_ALL_FIELDS');
      return;
    }

    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      this.errorMessage.set('USER_NOT_AUTHORIZED');
      return;
    }

    const success = this.authService.changePassword(
      currentUser.getLogin(),
      this.oldPassword,
      this.newPassword,
    ).subscribe(success=>{
      if(success){
         this.successMessage.set('PASSWORD_CHANGED_SUCCESS');
      this.oldPassword = '';
      this.newPassword = '';
      form.resetForm();
    } else {
      this.errorMessage.set('INVALID_OLD_PASSWORD');
    }
      }
    )

    
  }
}
