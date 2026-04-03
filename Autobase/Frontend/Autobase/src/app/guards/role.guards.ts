import { inject } from '@angular/core';
import { Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/authService';

export const canActivateRole = (route: ActivatedRouteSnapshot): boolean => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const requiredRoles = route.data['roles'] as string[];
  const userRoles = authService.getCurrentUser()?.roles ?? [];
  const hasRole = requiredRoles.some((role) => userRoles.includes(role));

  if (!hasRole) {
    router.navigate(['/login']);
    return false;
  }

  return true;
};
