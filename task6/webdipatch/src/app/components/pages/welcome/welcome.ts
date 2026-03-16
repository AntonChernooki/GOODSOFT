import { Component } from '@angular/core';
import { UserService } from '../../../service/user-service';
import { User } from '../../../models/user';
import { AuthService } from '../../../service/auth';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-welcome',
  imports: [CommonModule,RouterLink,TranslateModule],
  templateUrl: './welcome.html',
  styleUrl: './welcome.css',
})
export class Welcome {
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
  ) {}

  currentUser: User | null = null;

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (this.currentUser === null) {
      this.router.navigate(['/login']);
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(["/login"]);
  }
}
