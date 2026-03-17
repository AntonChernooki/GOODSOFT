import { Injectable } from '@angular/core';
import { AuthService } from '../service/auth-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard  {
  
constructor(private authService : AuthService,private router:Router){}


  canActivate():boolean {
    if(this.authService.isAuthenticated()){
      return true;
    }else{
      this.router.navigate(['/login']);
      return false;
    }
  }
}
