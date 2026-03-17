import { Injectable } from '@angular/core';
import { AuthService } from '../service/auth-service';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { Role } from '../models/role';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}
  canActivate(route: ActivatedRouteSnapshot) {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      this.router.navigate(['/login']);
      return false;
    }
    const needRoles = route.data['roles'] as Role[];
    if (!needRoles || needRoles.length === 0) {
      return true;
    }

    const userRoles = currentUser.getRoles();
    const hasRole = needRoles.some((role) => userRoles.has(role));
    if (!hasRole) {
      this.router.navigate(['/welcome']);
      return false;
    }

    return true;
  }
}
