import { Component } from '@angular/core';
import { AuthService } from '../../services/authService';
import { routes } from '../../app.routes';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  constructor(public authService : AuthService, public router:Router){}

  public logout():void{
    this.router.navigateByUrl("/login")
  }
  

}
