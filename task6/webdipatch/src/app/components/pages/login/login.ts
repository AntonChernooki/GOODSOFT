import { Component, signal } from '@angular/core';
import { AuthService } from '../../../service/auth-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';


@Component({
  selector: 'app-login',
  imports: [FormsModule, TranslateModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {
  login = '';
  password = '';
  errorMessage = signal<string>(''); 

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/welcome']);
    }
  }

  onSubmit(): void {
    
    this.errorMessage.set('');

    if (this.authService.login(this.login, this.password)) {
      this.router.navigate(['/welcome']);
    } else {
      this.errorMessage.set('INVALID_CREDENTIALS');
    }
  }
}